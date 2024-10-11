package com.personal.animeshpandey.stockup.Model

import com.google.gson.annotations.SerializedName


data class SearchMatchResponseObject(
    @SerializedName("bestMatches")
    val obtainedPossibleStocks:List<PossibleStocks>
)
