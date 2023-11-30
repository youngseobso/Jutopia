package stock.common

import BottomTabBar
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.AlertDialog
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import co.touchlab.kermit.Logger
import common.TopPageBar
import moe.tlaster.precompose.navigation.BackHandler
import moe.tlaster.precompose.navigation.Navigator
import stock.stockchart.StockChartPage
import stock.stocktrade.StockTradePage
private val log = Logger.withTag("StockAPI")

@Composable
fun StockScreen(
    stockId: String,
    stockCode: String,
    navigator: Navigator,
    viewModel: StockViewModel = moe.tlaster.precompose.viewmodel.viewModel(modelClass = StockViewModel::class) {
        StockViewModel(stockId, stockCode)
    }
) {

    val loadingState by viewModel.loadingState.collectAsState()
    val stock by viewModel.currentStock.collectAsState()
    log.i { stock.toString() }
    val currentPage by viewModel.currentPage.collectAsState()
    var showDialog by remember { mutableStateOf(false) }

    CustomBackHandler(onBack = {}, navigator = navigator, viewModel)
    when (loadingState) {
        StockLoadingState.LOADING -> {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxSize()
            ) {
                CircularProgressIndicator()
            }
        }
        StockLoadingState.LOADED -> {
            Column(
                modifier = Modifier.padding(bottom = 80.dp).fillMaxHeight()
            ) {
                TopPageBar("주식", navigator)
                StockRow(stock!!, {})
                Column {
                    when (currentPage) {
                        PageType.CHART -> {
                            StockChartPage(
                                stockId,
                                stockCode,
                                navigator = navigator,
                                stockViewModel = viewModel,
                            )
                        }
                        PageType.TRADE -> {
                            StockTradePage(stockId, navigator = navigator, stockViewModel = viewModel)
                        }
                    }

                }
            }
            BottomTabBar(navigator, 0)
        }
        StockLoadingState.ERROR -> {
            showDialog = true
        }

    }
    if (showDialog) {
        AlertDialog(
            onDismissRequest = {
                showDialog = false
            },
            title = {
                Text(text = "서버 에러")
            },
            text = {
                Text("서버에 문제가 발생했습니다. 다시 시도해 주세요.")
            },
            confirmButton = {
                TextButton(onClick = {
                    showDialog = false
                    navigator.goBack()
                }) {
                    Text("확인")
                }
            }
        )
    }

}

@Composable
fun CustomBackHandler(
    onBack: () -> Unit,
    navigator: Navigator,
    viewModel: StockViewModel
) {
    val currentOnBackPressed by rememberUpdatedState(onBack)
    var currentPage = viewModel.currentPage
    BackHandler {
        when (currentPage.value) {
            PageType.TRADE -> {
                viewModel.changePage(PageType.CHART)
            }
            else -> {
                currentOnBackPressed()
                navigator.goBack() // 원래 뒤로가기 동작
            }
        }
    }
}
