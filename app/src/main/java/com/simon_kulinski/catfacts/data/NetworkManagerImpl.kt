package com.simon_kulinski.catfacts.data

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkRequest
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.simon_kulinski.catfacts.common.isValueEmpty
import com.simon_kulinski.catfacts.domain.repositories.NetworkManager


class NetworkManagerImpl(private val context: Context) : NetworkManager {


    private val liveData = MutableLiveData<Boolean>()


    private val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    private val networkRequest = NetworkRequest.Builder().build()

    private val networkCallback = object : ConnectivityManager.NetworkCallback() {
        override fun onAvailable(network: Network) {
            liveData.postValue(true)
            super.onAvailable(network)
        }

        override fun onLost(network: Network) {
            liveData.postValue(false)
            super.onLost(network)
        }
    }

    override fun isNetworkAvailable(): LiveData<Boolean> {
        registerCallBack()
        if (liveData.isValueEmpty())
            liveData.postValue(false)
        return liveData
    }

    private fun registerCallBack() {
        connectivityManager.registerNetworkCallback(networkRequest, networkCallback)
    }
}
