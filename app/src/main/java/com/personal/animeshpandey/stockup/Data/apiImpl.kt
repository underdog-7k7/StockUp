package com.personal.animeshpandey.stockup.Data

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object retrofitObjectforStocks {
    private const val baseUrl = "https://www.alphavantage.co/"

    val api:apiOverview by lazy { //impls the overview we defined earlier
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(apiOverview::class.java)
    }
}