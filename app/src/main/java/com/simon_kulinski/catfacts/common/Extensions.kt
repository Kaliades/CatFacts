package com.simon_kulinski.catfacts.common

import androidx.lifecycle.MutableLiveData

fun <T> MutableLiveData<T>.isEmpty(): Boolean {
    return value == null
}