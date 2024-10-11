package com.personal.animeshpandey.stockup.ui

import androidx.compose.animation.animateColor
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.MutatePriority
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.personal.animeshpandey.stockup.Model.Stock
import com.personal.animeshpandey.stockup.Model.stockMetaData
import com.personal.animeshpandey.stockup.ui.theme.anti
import com.personal.animeshpandey.stockup.ui.theme.backGround
import com.personal.animeshpandey.stockup.ui.theme.backGroundDark
import com.personal.animeshpandey.stockup.ui.theme.primary
import com.personal.animeshpandey.stockup.ui.theme.secondary
import com.personal.animeshpandey.stockup.ui.theme.stocksFont
import com.personal.animeshpandey.stockup.ui.viewmodel.StockViewModel

@Composable
fun StockCard(stockDetails: Pair<Stock,stockMetaData>, modifier: Modifier = Modifier) {

    val stock = stockDetails.first
    val metadata = stockDetails.second

    var moreDetailsButton by remember {
        mutableStateOf(false)
    }


    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
            .height(IntrinsicSize.Min),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
        ) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween){
                Card(modifier= Modifier
                    .height(IntrinsicSize.Min),shape = RoundedCornerShape(12.dp),
                    elevation = CardDefaults.cardElevation(16.dp), colors = CardDefaults.cardColors(containerColor = secondary)) {
                    Text(
                        text = stock.symbol,
                        style = MaterialTheme.typography.bodyLarge.copy(
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = stocksFont
                        ),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier=Modifier.padding(4.dp),
                        color = Color.White
                    )
                }

                val changeValue = stock.change.toFloat()
                val changeColor = if (changeValue > 0) Color.Green else Color.Red

                Card(modifier= Modifier
                    .height(IntrinsicSize.Min),shape = RoundedCornerShape(12.dp),
                    elevation = CardDefaults.cardElevation(16.dp), colors = CardDefaults.cardColors(containerColor = changeColor)) {
                    Text(
                        text = "${stock.changePercent}",
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontSize = 20.sp,
                            fontWeight = FontWeight.SemiBold,
                            fontFamily = stocksFont,

                        ),
                        color = Color.White,
                        modifier = Modifier.padding(4.dp)
                    )
                }

            }


            Spacer(modifier = Modifier.height(4.dp))
            Text(text = metadata.name, style = MaterialTheme.typography.bodySmall.copy(
                fontSize = 16.sp,
                fontWeight = FontWeight.Normal,
                fontFamily = stocksFont
            ))
            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Price: ${stock.price} ${metadata.currency}",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface,
                fontFamily = stocksFont
            )

            Text(
                text = "High: ${stock.high} ${metadata.currency} | Low: ${stock.low} ${metadata.currency}",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface,
                fontFamily = stocksFont

            )

            Text(
                text = "Volume: ${americanTextRepr(stock.volume)}",
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.End,
                color = MaterialTheme.colorScheme.onSurface,
                fontFamily = stocksFont
            )

            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center, modifier = Modifier.fillMaxWidth()) {
                IconButton(onClick = { moreDetailsButton = !moreDetailsButton }, colors = IconButtonDefaults.iconButtonColors(containerColor = secondary)) {
                    if(moreDetailsButton){
                        Icon(imageVector = Icons.Default.KeyboardArrowUp, contentDescription = "more details",modifier=Modifier.size(32.dp), tint = Color.White)
                    }else{
                        Icon(imageVector = Icons.Default.KeyboardArrowDown, contentDescription = "more details",modifier=Modifier.size(32.dp), tint = Color.White)
                    }

                }
            }

            if(moreDetailsButton){
                moreInfoBar(stock,metadata)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewStockCard() {
    val stockObj = Stock(
        symbol = "DELL",
        price = "174.55",
        open = "172.35",
        high = "175.00",
        low = "170.25",
        volume = "10000",
        latestTradingDay = "2024-10-10",
        previousClose = "172.22",
        change = "+2.33",
        changePercent = "+1.35%"
    )

    val metaData = stockMetaData(
        "DELL TECHS INC. C  DL-01",
        "United States",
        "My type",
        "EUR"
    )

    StockCard(
        Pair(stockObj,metaData)
    )
}

@Composable
fun moreInfoBar(stock:Stock, metaData: stockMetaData){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = "More details",
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Normal,
                    fontFamily = stocksFont
                )
            )
            Divider(color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f))

            Text(text = "Region: ${metaData.region}", style = MaterialTheme.typography.bodyMedium, fontFamily = stocksFont)
            Text(text = "Last Trading Day: ${stock.latestTradingDay}", style = MaterialTheme.typography.bodyMedium,fontFamily = stocksFont)
            Text(text = "Absolute Change: ${stock.change}", style = MaterialTheme.typography.bodyMedium,fontFamily = stocksFont)
            Text(text = "Opened At: ${stock.open}", style = MaterialTheme.typography.bodyMedium,fontFamily = stocksFont)
            Text(text = "Previous Close: ${stock.previousClose}", style = MaterialTheme.typography.bodyMedium,fontFamily = stocksFont)

            Divider(color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f))
        }
    }
}


fun americanTextRepr(numberStr: String): String {
    val number = numberStr.replace(",", "").toLong()

    return when {
        number >= 1_000_000_000_000 -> "${number / 1_000_000_000_000} Trillion"
        number >= 1_000_000_000 -> "${number / 1_000_000_000} Billion"
        number >= 1_000_000 -> "${number / 1_000_000} Million"
        number >= 1_000 -> "${number / 1_000} Thousands"
        else -> number.toString()
    }
}
