package com.personal.animeshpandey.stockup.Data

import com.personal.animeshpandey.stockup.BuildConfig
import com.personal.animeshpandey.stockup.Model.ResponseObject
import retrofit2.http.GET
import retrofit2.http.Query

interface apiOverview {
    @GET("/query?function=GLOBAL_QUOTE&symbol=IBM&apikey=demo")
    suspend fun getStockResponse(
        @Query("function") function: String = "GLOBAL_QUOTE",
        @Query("symbol") company: String,
        @Query("apikey") apikey:String = BuildConfig.AlphaVantageKey
    ): ResponseObject
}