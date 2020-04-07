package com.simon_kulinski.catfacts.data

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

interface CatFactService {

    @GET(LIST_OF_FACTS_PATH)
    suspend fun getListOfCatFacts(): List<ApiCatFact>

    @GET("{id}")
    suspend fun getCatFactsById(@Path("id") id: String): ApiCatFact


    companion object {
        private const val LIST_OF_FACTS_PATH = "random?amount=30"
        private const val BASE_URL = "https://cat-fact.herokuapp.com/facts/"

        private val okHttpClient = OkHttpClient
            .Builder()
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .build()
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(OkHttpClient())
            .build()
            .create(CatFactService::class.java)

    }


}