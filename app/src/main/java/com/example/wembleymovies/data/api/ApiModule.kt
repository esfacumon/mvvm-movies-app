package com.example.wembleymovies.data.api

import com.example.wembleymovies.data.api.ApiConstants.URI
import com.example.wembleymovies.data.api.ApiService

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * This object (singleton pattern: unique instance of ApiModule class) will be used to make api requests.
 */
object ApiModule {
    /**
     * @return OkHttpClient class from library OkHttp used to make http requests. Adds logging to make debugging easier.
     */
    private fun provideOkHttpClient(): OkHttpClient {
        val logging = HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }
        return OkHttpClient.Builder().addInterceptor(logging).build()
    }


    /**
     * @return Retrofit instance based on our configuration (URI). Uses Gsonconverter to convert from JSON to Kotlin object.
     */
    private fun provideRetrofit(client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(URI)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }


    /**
     * @return ApiService using previous functions.
     */
    fun provideApiService(): ApiService {
        return provideRetrofit(provideOkHttpClient()).create(ApiService::class.java)
    }
}