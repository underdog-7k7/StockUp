package com.personal.animeshpandey.stockup.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.personal.animeshpandey.stockup.RotateDotAnimation
import com.personal.animeshpandey.stockup.ui.StockCard
import com.personal.animeshpandey.stockup.ui.theme.backGround
import com.personal.animeshpandey.stockup.ui.theme.primary
import com.personal.animeshpandey.stockup.ui.viewmodel.StockViewModel
import com.personal.animeshpandey.stockup.ui.viewmodel.screenUiState


@Composable
fun mainScreen(){
    val viewModel:StockViewModel = viewModel()
    var search by remember { mutableStateOf("") }
    val uiState by viewModel.currentStateViewonly.collectAsState()


    Column(modifier = Modifier
        .fillMaxSize()
        .background(color = primary)) {
        Text(text = "Stock Up")
        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp), horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically) {
            OutlinedTextField(value = search, onValueChange = {search=it}, shape= RoundedCornerShape(16.dp))
            IconButton(onClick = { viewModel.fetchStock(search) }, enabled = (search.isNotBlank()&& uiState !is screenUiState.waiting)) {
                Icon(imageVector = Icons.Rounded.Search, contentDescription = "search stock")
            }
        }

        Column(modifier = Modifier
            .fillMaxSize()
            .background(color = Color.Red)) {
            when(uiState){
                is screenUiState.initial->{
                    Text(text = "Search for your favourite stock to begin")}
                is screenUiState.waiting->{
                    Row(modifier = Modifier.fillMaxWidth()) {
                        Text(text = "Fetching stocks")
//                        LinearProgressIndicator()
//                        CircularProgressIndicator()
//                        CircularProgressIndicator(progress = 0.89f)
//                        RotateDotAnimation()

                    }
                }
                is screenUiState.responseSuccess->{
                    val stocksAndDetails = (uiState as screenUiState.responseSuccess).stocksAndDetails
                    Column {
                        for(i in stocksAndDetails){
                            if(i!=null){
                                StockCard(stockDetails = i)
                            }
                            else{
                                continue
                            }
                        }
                    }

                }
                else->{
                    Text(text = (uiState as screenUiState.responseError).message)
                }

            }
        }
    }

}

@Preview(showBackground = true)
@Composable
fun test(){
    mainScreen()
}
