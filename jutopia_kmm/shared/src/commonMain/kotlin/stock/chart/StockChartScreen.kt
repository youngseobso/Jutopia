package stock.chart

import BottomTabBar
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import common.TopPageBar
import lease.LeasePage
import moe.tlaster.precompose.navigation.BackStackEntry
import moe.tlaster.precompose.navigation.Navigator

@Composable
fun StockChartScreen(backStackEntry: BackStackEntry,navigator: Navigator) {
    Column {
        TopPageBar("주식정보")
        StockChartPage()
    }
    BottomTabBar(navigator)
}
