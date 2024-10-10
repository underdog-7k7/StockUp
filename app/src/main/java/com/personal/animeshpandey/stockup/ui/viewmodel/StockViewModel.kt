package com.personal.animeshpandey.stockup.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.personal.animeshpandey.stockup.Data.retrofitObjectforStocks
import com.personal.animeshpandey.stockup.Model.ResponseObject
import com.personal.animeshpandey.stockup.Model.Stock
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
                _currentState.value  = screenUiState.waiting
                val response = api.getStockResponse(company = search)
                if(response.obtainedStock.symbol==null){
                    _currentState.value = screenUiState.responseError("Could not find a stock with this name, a typo maybe?")
                }else{
                    _currentState.value = screenUiState.responseSuccess(response.obtainedStock)
                }
            }catch (e:Exception){
                _currentState.value = screenUiState.responseError(e.message ?: "unknown error")
            }
        }
    }
}

sealed class screenUiState{
    object initial:screenUiState()
    object waiting:screenUiState()
    data class responseSuccess(val stock:Stock):screenUiState()
    data class responseError(val message:String):screenUiState()
}