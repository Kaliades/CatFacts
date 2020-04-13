package com.simon_kulinski.catfacts.domain.repositories

import androidx.lifecycle.LiveData

interface NetworkManager {

    fun isNetworkAvailable(): LiveData<Boolean>

}