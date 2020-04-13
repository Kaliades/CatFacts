package com.simon_kulinski.catfacts.ui.cat_facts_list

import com.jraska.livedata.test
import com.simon_kulinski.catfacts.BaseTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import okhttp3.mockwebserver.MockResponse
import org.junit.Before
import org.junit.Test
import org.koin.test.inject
import java.net.HttpURLConnection

@ExperimentalCoroutinesApi
class ListViewModelTest : BaseTest() {

    private val viewModel: ListViewModel by inject()

    @Before
    fun setResponse() {
        val mockResponse = MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_OK)
        mockWebServer.enqueue(mockResponse)
    }

    @Test
    fun `Does the progressBar shows during loading a data`() {
        viewModel.progressBarLiveData.test().assertValue(true)
    }

    @Test
    fun `Does the progressBar hides after the data has been loaded`() {
        val mockResponse = MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_OK)
        mockWebServer.enqueue(mockResponse)
        viewModel.progressBarLiveData.test().awaitNextValue().assertValue(false)
    }


}