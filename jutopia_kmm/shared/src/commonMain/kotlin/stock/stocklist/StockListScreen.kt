package stock.stocklist

import BottomTabBar
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import common.TopPageBar
import lease.LeasePage
import moe.tlaster.precompose.navigation.Navigator

@Composable
fun StockListScreen(navigator: Navigator) {
    Column {
        TopPageBar("주식", navigator)
        StockListPage()
    }
    BottomTabBar(navigator)
}
