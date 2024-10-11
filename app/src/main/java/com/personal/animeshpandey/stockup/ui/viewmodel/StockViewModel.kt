package com.personal.animeshpandey.stockup.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.personal.animeshpandey.stockup.Data.retrofitObjectforStocks
import com.personal.animeshpandey.stockup.Model.PossibleStocks
import com.personal.animeshpandey.stockup.Model.Stock
import com.personal.animeshpandey.stockup.Model.stockMetaData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class StockViewModel :ViewModel(){
    private val api = retrofitObjectforStocks.api

    private val _currentState = MutableStateFlow<screenUiState>(screenUiState.initial)
    val currentStateViewonly= _currentState.asStateFlow()


    //entrypoint
    fun fetchStock(search: String) {
        viewModelScope.launch {
            try {
                Log.d("StockViewModel", "Starting stock search for: $search")
                _currentState.value = screenUiState.waiting("Looking up stocks...")

                val possibleStocks = searchPossibleStocks(search)
                if (possibleStocks != null) {
                    fetchTopStocks(possibleStocks)
                } else {
                    updateAllStateError("The connection broke off, try again...")
                }
            } catch (e: Exception) {
                updateAllStateError(e.message ?: "idk")
            }
        }
    }


    // relevant stocx for the search input
    private suspend fun searchPossibleStocks(search: String): List<PossibleStocks>? {
        val possibleStocks = retrofitObjectforStocks.api.getPossibleStocks(keywords = search)
        Log.d("StockViewModel2","")

        if (possibleStocks.obtainedPossibleStocks != null && possibleStocks.obtainedPossibleStocks.isNotEmpty()) {
            _currentState.value = screenUiState.waiting("Fetching relevant stocks...")
            Log.d("StockViewModel tester", possibleStocks.obtainedPossibleStocks[0].toString())
            return possibleStocks.obtainedPossibleStocks
        } else {
            updateAllStateError("No entities for the given search... A typo maybe?")
            return null
        }
    }

    private suspend fun fetchTopStocks(possibleStocks: List<PossibleStocks>) {
        _currentState.value = screenUiState.waiting("Displaying top 3 relevant results...") //limited api calls in alpha vantage, so fetch reqs for only 3

        withContext(Dispatchers.IO) {
            try {
                val first3_OrLess = possibleStocks.take(3).map { possibleStock ->
                    async {
                        val stock = fetchStockDetails(possibleStock.symbol)
                        if(stock!=null){
                            val metaData = stockMetaData(possibleStock.name,possibleStock.region,possibleStock.type,possibleStock.currency)
                            Pair(stock,metaData)
                        }else{
                            null
                        }
                    }
                }

                val stocksWithDetails = first3_OrLess.awaitAll().filterNotNull()

                if (stocksWithDetails.isNotEmpty()) {
                    _currentState.value = screenUiState.responseSuccess(stocksWithDetails)
                } else {
                    updateAllStateError("Problem while fetching stock details...")
                }
            } catch (e: Exception) {
                updateAllStateError("Unable to fetch details for corresponding stocks")
            }
        }
    }

    private suspend fun fetchStockDetails(symbol: String): Stock? {
        return try {
            val response = retrofitObjectforStocks.api.getStockResponse(company = symbol)
            Log.d("StockViewModel9", response.obtainedStock.toString())
            response.obtainedStock
        } catch (e: Exception) {
            Log.d("StockViewMoel10", "Error fetching details for: $symbol")
            null
        }
    }
    private fun updateAllStateError(message: String) {
        Log.d("StockViewModelError", message)
        _currentState.value = screenUiState.responseError(message)
    }

}

sealed class screenUiState{
    object initial:screenUiState()
    data class waiting(val message:String):screenUiState()
    data class responseSuccess(val stocksAndDetails: List<Pair<Stock, stockMetaData>>):screenUiState()
    data class responseError(val message:String):screenUiState()
}