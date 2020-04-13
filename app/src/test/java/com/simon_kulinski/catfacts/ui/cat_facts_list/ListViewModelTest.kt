package com.simon_kulinski.catfacts.ui.cat_facts_list

import androidx.lifecycle.MutableLiveData
import com.jraska.livedata.test
import com.simon_kulinski.catfacts.BaseTest
import com.simon_kulinski.catfacts.domain.repositories.NetworkManager
import kotlinx.coroutines.ExperimentalCoroutinesApi
import okhttp3.mockwebserver.MockResponse
import org.junit.Before
import org.junit.Test
import org.koin.test.inject
import org.mockito.Mockito
import java.net.HttpURLConnection
import java.util.concurrent.TimeUnit

@ExperimentalCoroutinesApi
class ListViewModelTest : BaseTest() {

    private val viewModel: ListViewModel by inject()
    private val networkManager: NetworkManager by inject()

    @Before
    fun setResponse() {
        val mockResponse = MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_OK)
        mockWebServer.enqueue(mockResponse)
    }

    @Test
    fun `Case of no network connection`() {
        Mockito.`when`(networkManager.isNetworkAvailable()).thenReturn(MutableLiveData(false))
        viewModel.isNetworkConnection.test().assertValue(false)
        viewModel.progressBarLiveData.test().assertValue(false)
        viewModel.liveDataListOfCatFactsResult.test().assertNoValue()
    }

    @Test
    fun `Case of network connection has been lost`() {
        val liveData = MutableLiveData(true)
        Mockito.`when`(networkManager.isNetworkAvailable()).thenReturn(liveData)
        viewModel.isNetworkConnection.test().assertValue(true)
        liveData.value = false
        viewModel.isNetworkConnection.test().assertValue(false)
        viewModel.progressBarLiveData.test().awaitNextValue().assertValue(false)
        viewModel.getNewData()
        viewModel.liveDataListOfCatFactsResult.test().awaitNextValue(10, TimeUnit.MILLISECONDS).assertHistorySize(1)
    }

    @Test
    fun `Does the progressBar shows during loading a data`() {
        Mockito.`when`(networkManager.isNetworkAvailable()).thenReturn(MutableLiveData(true))
        viewModel.isNetworkConnection.test().assertValue(true)
        viewModel.progressBarLiveData.test().assertValue(true)
    }

    @Test
    fun `Does the progressBar hides after the data has been loaded`() {
        Mockito.`when`(networkManager.isNetworkAvailable()).thenReturn(MutableLiveData(true))
        viewModel.isNetworkConnection.test().assertValue(true)
        viewModel.progressBarLiveData.test().awaitNextValue().assertValue(false)
    }


}