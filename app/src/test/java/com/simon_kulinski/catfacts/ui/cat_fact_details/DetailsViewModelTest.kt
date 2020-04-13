package com.simon_kulinski.catfacts.ui.cat_fact_details

import com.jraska.livedata.test
import com.simon_kulinski.catfacts.BaseTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import okhttp3.mockwebserver.MockResponse
import org.junit.Before
import org.junit.Test
import org.koin.core.parameter.parametersOf
import org.koin.test.inject
import java.net.HttpURLConnection

@ExperimentalCoroutinesApi
class DetailsViewModelTest : BaseTest() {

    private val viewModel: DetailsViewModel by inject { parametersOf("") }

    @Before
    fun setResponse(){
        val mockResponse = MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_OK)
        mockWebServer.enqueue(mockResponse)
    }

    @Test
    fun `Does the progressBar shows during loading a data`() {
        viewModel.initData()
        viewModel.progressBarLiveData.test().assertValue(true)
    }

    @Test
    fun `Does the progressBar hides after the data has been loaded`() {
        val mockResponse = MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_OK)
        mockWebServer.enqueue(mockResponse)
        viewModel.initData()
        viewModel.progressBarLiveData.test().awaitNextValue().assertValue(false)
    }

}