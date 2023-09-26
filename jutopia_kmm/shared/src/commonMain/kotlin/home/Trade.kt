package home

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import co.touchlab.kermit.Logger
import moe.tlaster.precompose.navigation.Navigator
import stock.stockchart.StockChartScreen

@Composable
fun Trade(navigator: Navigator) {
//    Text("환전")
    StockChartScreen("AAPL", navigator)
}
