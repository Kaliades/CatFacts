package com.simon_kulinski.catfacts.common

import androidx.lifecycle.MutableLiveData

fun <T> MutableLiveData<T>.isValueEmpty(): Boolean {
    return value == null
}