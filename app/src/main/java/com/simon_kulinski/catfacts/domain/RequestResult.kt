package com.simon_kulinski.catfacts.domain

class RequestResult<out T>
private constructor(
    val value: T?,
    val error: Exception?,
    val hasError: Boolean
) {


    companion object {
        fun onFailure(error: Exception) = RequestResult(null, error, true)
        fun <T> onSuccess(value: T) = RequestResult(value, null, false)
    }
}