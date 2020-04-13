package com.simon_kulinski.catfacts.data

import android.text.format.DateFormat
import com.simon_kulinski.catfacts.data.network.CatFactsService
import com.simon_kulinski.catfacts.domain.models.CatFact
import com.simon_kulinski.catfacts.domain.repositories.CatFactsRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import java.net.HttpURLConnection
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.util.*

@ExperimentalCoroutinesApi
class CatFactsRepositoryImplTest {

    private val mockWebServer = MockWebServer()
    private lateinit var repository: CatFactsRepository

    @Before
    fun setUp() {
        mockWebServer.start()
        val mocUrl = mockWebServer.url("/")
        repository = CatFactsRepositoryImpl(
            CatFactsService.createCatFactService(mocUrl),
            TestCoroutineDispatcher()
        )
    }


    @After
    fun shutdown() {
        mockWebServer.shutdown()
    }

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
            assertEquals("Expected true for no_connection", true, result.hasError)
        }
    }
}