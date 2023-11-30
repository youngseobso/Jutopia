package stock.stockchart

import BottomTabBar
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import common.TopPageBar
import moe.tlaster.precompose.navigation.Navigator


@Composable
fun StockChartScreen(stockId: String,stockCode: String, navigator: Navigator) {
    Column(
        modifier = Modifier.fillMaxHeight().padding(bottom = 100.dp)
    ) {
        TopPageBar(stockId, navigator)
        StockChartPage(stockId, stockCode, navigator=navigator)
    }
    BottomTabBar(navigator, 0)
}
