package home

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import moe.tlaster.precompose.navigation.Navigator
import stock.stocklist.StockListScreen

@Composable
fun Stock(navigator: Navigator) {
    StockListScreen(navigator)
}