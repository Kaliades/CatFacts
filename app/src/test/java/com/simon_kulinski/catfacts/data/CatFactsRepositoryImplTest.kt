package com.simon_kulinski.catfacts.data

import com.simon_kulinski.catfacts.BaseTest
import com.simon_kulinski.catfacts.domain.models.CatFact
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import org.junit.Assert.assertEquals
import org.junit.Test
import org.koin.test.inject
import java.net.HttpURLConnection
import java.sql.Timestamp

@ExperimentalCoroutinesApi
class CatFactsRepositoryImplTest : BaseTest() {

    private val repository: CatFactsRepositoryImpl by inject()


    @Test
    fun getListOfCatFacts() {
        val mockResponse = MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_OK)
            .setBody(readListFromFile())
        mockWebServer.enqueue(mockResponse)
        runBlocking {
            val results = repository.getListOfCatFacts()
            assertEquals("List should contains a 2 items.", 2, results.value!!.size)
        }
    }

    private fun readListFromFile() =
        ClassLoader.getSystemResource("json_list.txt").readText()


    @Test
    fun getCatFact() {
        val mockResponse = MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_OK)
            .setBody(readItemFromFile())
        mockWebServer.enqueue(mockResponse)
        val item = CatFact(
            id = "5c609a02e549020014533039",
            text = "All Scottish Folds descended from Susie, a white barn cat discovered in Scotland in the early 1960s." +
                    " Susie sported the breed's folded ears – the result of a genetic mutation – and passed on the trait through breeding.",
            updateTime = Timestamp(1577930568616)
        )
        runBlocking {
            val results = repository.getCatFact("")
            assertEquals("CatFacts should be the same.", item, results.value!!)
        }
    }

    private fun readItemFromFile() =
        ClassLoader.getSystemResource("json_item.txt").readText()

    @Test
    fun error() {
        val mockResponse = MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_BAD_REQUEST)
        mockWebServer.enqueue(mockResponse)
        runBlocking {
            val result = repository.getCatFact("")
            assertEquals("Expected true for no_connection_screen", true, result.hasError)
        }
    }
}