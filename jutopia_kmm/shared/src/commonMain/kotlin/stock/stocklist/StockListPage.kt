package stock.stocklist

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Switch
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import co.touchlab.kermit.Logger
import moe.tlaster.precompose.navigation.Navigator
import stock.common.StockRow

private val log = Logger.withTag("StockListPage")

@Composable
fun StockListPage(
    viewModel: StockListViewModel = moe.tlaster.precompose.viewmodel.viewModel(modelClass = StockListViewModel::class) {
        StockListViewModel()
    }, modifier: Modifier = Modifier,
    navigator: Navigator
) {
    val stocks by viewModel.stocks.collectAsState();
    var showOwnedOnly by remember { mutableStateOf(false) }
    val filteredStocks = if (showOwnedOnly) stocks.filter { it.isOwnedByUser } else stocks

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(13.dp),
        horizontalArrangement = Arrangement.End
    ) {
        Text("보유 주식만 보기", fontWeight = FontWeight.Bold, modifier = Modifier.padding(top = 11.dp))
        Switch(checked = showOwnedOnly, onCheckedChange = { showOwnedOnly = it })
    }

    LazyColumn(modifier = Modifier.padding(13.dp)) {
        items(filteredStocks) { stock ->
            StockRow(stock) {
                navigator.navigate("/stockChart/${stock.id}/${stock.stockCode}")
            }
        }
    }

}





