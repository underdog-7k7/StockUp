package com.personal.animeshpandey.stockup.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.personal.animeshpandey.stockup.Data.retrofitObjectforStocks
import com.personal.animeshpandey.stockup.Model.PossibleStocks
import com.personal.animeshpandey.stockup.Model.ResponseObject
import com.personal.animeshpandey.stockup.Model.Stock
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class StockViewModel :ViewModel(){
    private val api = retrofitObjectforStocks.api

    private val _currentState = MutableStateFlow<screenUiState>(screenUiState.initial)
    val currentStateViewonly= _currentState.asStateFlow()

    fun fetchStock(search:String){
        viewModelScope.launch {
            try {
                _currentState.value  = screenUiState.waiting("Looking up stocks...")
                val possibleStocks = api.getPossibleStocks(keywords = search)
                if(possibleStocks.obtainedPossibleStocks!=null){
                    if(possibleStocks.obtainedPossibleStocks.isNotEmpty()){
                        _currentState.value = screenUiState.waiting("Fetching relevant stocks...")
                    }else{
                        _currentState.value = screenUiState.responseError("No entities for the given search..a typo maybe?")
                        return@launch
                    }
                }else{
                    _currentState.value = screenUiState.responseError("The connection broke off, try again...")
                    return@launch
                }

                //since the api calls are limited(15req/day for alpha vantage) , i am fetching the stock details for the first 3 most relevant stocks only
                _currentState.value = screenUiState.waiting("Displaying top 3 relevant results...")
                CoroutineScope(Dispatchers.IO).launch{
                    try {
                        val first3orLessOrNull = possibleStocks.obtainedPossibleStocks.take(3).map{
                            possibleStock->
                            async {
                                try {
                                    val response = api.getStockResponse(company = possibleStock.symbol)
                                    response.obtainedStock
                                }catch (e:Exception){
                                    null
                                }

                            }
                        }
                        val stocksWithDetails = first3orLessOrNull.awaitAll()
                        if(stocksWithDetails!=null){
                            _currentState.value = screenUiState.responseSuccess(stocksWithDetails)
                        }



                    }catch (e:Exception){

                    }
                }


                if(response.obtainedStock.symbol==null){
                    _currentState.value = screenUiState.responseError("Could not find a stock with this name, a typo maybe?")
                }else{
                    _currentState.value = screenUiState.responseSuccess(stocksWithDetails)
                }
            }catch (e:Exception){
                _currentState.value = screenUiState.responseError(e.message ?: "unknown error")
            }
        }
    }

//    fun fetchPossibleStocks(search:String):List<PossibleStocks>?{
//        viewModelScope.launch {
//            try {
//                _currentState.value= screenUiState.waiting("Looking up stocks for that search...")
//                val possibleStocks = api.getPossibleStocks(keywords = search)
//                if(possibleStocks!=null){
//                    if(possibleStocks.obtainedPossibleStocks.isNotEmpty()){
//
//                    }
//                }else{
//                    _currentState.value = screenUiState.responseError("Connection interrupted!, retry ")
//                }
//            }
//            catch (e:Exception){
//                _currentState.value = screenUiState.responseError("Could not find matching stocks!")
//                return null
//            }
//        }
//    }

}

sealed class screenUiState{
    object initial:screenUiState()
    data class waiting(val message:String):screenUiState()
    data class responseSuccess(val stocksAndDetails:List<Stock>):screenUiState()
    data class responseError(val message:String):screenUiState()
}