package com.personal.animeshpandey.stockup.Model

import com.google.gson.annotations.SerializedName

data class PossibleStocks(
    @SerializedName("01. symbol")
    val symbol: String,
    @SerializedName("02. name")
    val name: String,
    @SerializedName("03. type")
    val type: String,
    @SerializedName("04. region")
    val region: String,
    @SerializedName("05. marketOpen")
    val marketOpen: String,
    @SerializedName("06. marketClose")
    val marketClose: String,
    @SerializedName("07. timezone")
    val timezone: String,
    @SerializedName("08. currency")
    val currency: String,
    @SerializedName("09. matchScore")
    val matchScore: String
)