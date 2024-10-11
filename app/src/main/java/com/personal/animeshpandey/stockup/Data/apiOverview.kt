package com.personal.animeshpandey.stockup.Data

import com.personal.animeshpandey.stockup.BuildConfig
import com.personal.animeshpandey.stockup.Model.ResponseObject
import com.personal.animeshpandey.stockup.Model.SearchMatchResponseObject
import retrofit2.http.GET
import retrofit2.http.Query


//@Query("apikey") apikey:String = BuildConfig.AlphaVantageKey
interface apiOverview {
    @GET("/query")
    suspend fun getStockResponse(
        @Query("function") function: String = "GLOBAL_QUOTE",
        @Query("symbol") company: String,
        @Query("apikey") apikey:String ="RKI65X3VAG02N4DB" //dummy api key , does not work
    ): ResponseObject

    @GET("/query")
    suspend fun getPossibleStocks(
        @Query("function") function:String = "SYMBOL_SEARCH",
        @Query("keywords") keywords:String,
        @Query("apikey") apikey:String ="RKI65X3VAG02N4DB"
     ): SearchMatchResponseObject
}

