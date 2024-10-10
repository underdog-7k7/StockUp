package com.personal.animeshpandey.stockup.Model

import com.google.gson.annotations.SerializedName

data class ResponseObject(
    @SerializedName("Global Quote")
    val obtainedStock: Stock
)
