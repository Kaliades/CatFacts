package com.simon_kulinski.catfacts.data.network

import okhttp3.HttpUrl
import okhttp3.HttpUrl.Companion.toHttpUrl
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

interface CatFactsService {

    @GET(LIST_OF_FACTS_PATH)
    suspend fun getListOfCatFacts(): List<ApiModel>

    @GET("{id}")
    suspend fun getCatFactsById(@Path("id") id: String): ApiModel


    companion object {
        private const val LIST_OF_FACTS_PATH = "random?amount=30"
        private const val BASE_URL = "https://cat-fact.herokuapp.com/facts/"

        private val okHttpClient = OkHttpClient
            .Builder()
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .build()
        private val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)


        /**
         * Creates an CatFactService.
         * In case of tests send a mock url.
         * @param url Default value is null
         * */
        fun createCatFactService(url: HttpUrl? = null): CatFactsService {
            return retrofit
                .baseUrl(url ?: BASE_URL.toHttpUrl())
                .build()
                .create(CatFactsService::class.java)
        }
    }
}