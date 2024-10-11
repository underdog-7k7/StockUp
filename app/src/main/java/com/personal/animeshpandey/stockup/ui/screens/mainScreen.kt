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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.personal.animeshpandey.stockup.R
import com.personal.animeshpandey.stockup.RotateDotAnimation
import com.personal.animeshpandey.stockup.ui.StockCard
import com.personal.animeshpandey.stockup.ui.errorscreen
import com.personal.animeshpandey.stockup.ui.initialScreen
import com.personal.animeshpandey.stockup.ui.theme.anti
import com.personal.animeshpandey.stockup.ui.theme.backGround
import com.personal.animeshpandey.stockup.ui.theme.primary
import com.personal.animeshpandey.stockup.ui.theme.secondary
import com.personal.animeshpandey.stockup.ui.theme.stocksFont
import com.personal.animeshpandey.stockup.ui.viewmodel.StockViewModel
import com.personal.animeshpandey.stockup.ui.viewmodel.screenUiState
import com.personal.animeshpandey.stockup.ui.waitingScreen


@Composable
fun mainScreen(){
    val viewModel:StockViewModel = viewModel()
    var search by remember { mutableStateOf("") }
    val uiState by viewModel.currentStateViewonly.collectAsState()


    val scrollState = rememberScrollState()

    Column(modifier = Modifier
        .fillMaxSize()
        .background(color = Color.White)
        ) {

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
            OutlinedTextField(modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
                ,value = search,
                onValueChange = {search = it},
                singleLine = true,
                shape = RoundedCornerShape(32.dp),
                colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = anti, focusedLabelColor = anti),
                label = { Text(text = "Search Stocks")},
                textStyle = TextStyle(fontFamily = stocksFont),
                trailingIcon = {
                    IconButton(onClick = {viewModel.fetchStock(search) },
                        enabled = (search.isNotBlank()&& uiState !is screenUiState.waiting),
                        colors = IconButtonDefaults.iconButtonColors(containerColor = anti)) {
                        Icon(imageVector = Icons.Default.Search, contentDescription = "search button", tint = Color.White)
                    }

                },
                leadingIcon = { Icon(modifier = Modifier
                    .size(32.dp)
                    .padding(4.dp), painter = painterResource(id = R.drawable.initial), contentDescription = "icon")}
            )
        }

        Column(modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)) {
            when(uiState){
                is screenUiState.initial->{
                    initialScreen()
                   }
                is screenUiState.waiting->{
                    waitingScreen(message = (uiState as screenUiState.waiting).message)
                }
                is screenUiState.responseSuccess->{
                    val stocksAndDetails = (uiState as screenUiState.responseSuccess).stocksAndDetails
                    Column(modifier = Modifier.verticalScroll(scrollState)) {
                        for(i in stocksAndDetails){
                            if(i!=null){
                                StockCard(stockDetails = i)
                            }
                            else{
                                continue
                            }
                        }
                        Spacer(modifier = Modifier.height(16.dp))
                    }
                }
                else->{
                    errorscreen(error = (uiState as screenUiState.responseError).message)
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
