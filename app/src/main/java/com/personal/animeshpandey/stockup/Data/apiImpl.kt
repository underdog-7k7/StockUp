package com.personal.animeshpandey.stockup.Data

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object retrofitObjectforStocks {
    private const val baseUrl = "https://www.alphavantage.co/"

    private val httplogs = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(httplogs)
        .build()

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val api: apiOverview by lazy {
        retrofit.create(apiOverview::class.java)
    }


}