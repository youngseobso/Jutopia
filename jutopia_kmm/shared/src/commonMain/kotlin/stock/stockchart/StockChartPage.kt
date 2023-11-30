package stock.stockchart

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import co.touchlab.kermit.Logger
import com.aay.compose.baseComponents.model.GridOrientation
import com.aay.compose.lineChart.LineChart
import com.aay.compose.lineChart.model.LineParameters
import com.aay.compose.lineChart.model.LineType
import moe.tlaster.precompose.navigation.Navigator
import stock.common.PageType
import stock.common.StockViewModel

private val log = Logger.withTag("stockChart")

@Composable
fun StockChartPage(
    stockId: String,
    stockCode: String,
    viewModel: StockChartViewModel = moe.tlaster.precompose.viewmodel.viewModel(modelClass = StockChartViewModel::class) {
        StockChartViewModel(stockId, stockCode)
    },
    stockViewModel: StockViewModel = moe.tlaster.precompose.viewmodel.viewModel(modelClass = StockViewModel::class) {
        StockViewModel(stockId, stockCode)
    },
    navigator: Navigator,
    modifier: Modifier = Modifier
) {
    val currentChartData by viewModel.currentChartData.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    if (isLoading || currentChartData.isEmpty()) {
        CircularProgressIndicator()
    } else{
        Column {
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                Button(
                    onClick = { viewModel.setTimeFrame(TimeFrame.minute) },
                    modifier = Modifier.fillMaxWidth().weight(1f),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Color(0xFFC3E0E8)
                    )
                ) {
                    Text("분")
                }
                Button(
                    onClick = { viewModel.setTimeFrame(TimeFrame.hour) },
                    modifier = Modifier.fillMaxWidth().weight(1f),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Color(0xFFC3E0E8)
                    )
                ) {
                    Text("시간")
                }
                Button(
                    onClick = { viewModel.setTimeFrame(TimeFrame.day) },
                    modifier = Modifier.fillMaxWidth().weight(1f),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Color(0xFFC3E0E8)
                    )
                ) {
                    Text("일")
                }
            }
            Box(
                modifier = Modifier.height(330.dp)
            ){
                ScrollableLineChart(currentChartData)

            }
            Button(
                onClick = {
                    stockViewModel.changePage(PageType.TRADE)
                },
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color(0xFFC3E0E8)
                ),
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text("주문")
            }
        }
    }

}


@Composable
fun ScrollableLineChart(chartData: List<Pair<String, Double?>>) {
    val scrollState = rememberScrollState()

    LaunchedEffect(Unit) {
        scrollState.scrollTo(scrollState.maxValue)
    }

    val processedData = mutableListOf<Double?>()
    var lastValidValue: Double? = 0.0
    chartData.map {
        if (it.second == null) {
            processedData.add(lastValidValue)
        } else {
            processedData.add(it.second)
            lastValidValue = it.second
        }
    }
    log.i{"null처리 데이터: $processedData"}

    val lineParameters: List<LineParameters> = listOf(
        LineParameters(
            label = "주식 가격",
            data = processedData as List<Double>,
            lineColor = Color.Gray,
            lineType = LineType.DEFAULT_LINE,
            lineShadow = false
        )
    )

    val xAxisData = chartData.map { it.first }

    log.i { "x축 데이터: $xAxisData" }

    Box(
        modifier = Modifier
            .horizontalScroll(scrollState)
            .width(((chartData.size / 3) * 300).dp)
            .fillMaxHeight()
    ) {
        LineChart(
            linesParameters = lineParameters,
            isGrid = true,
            xAxisData = xAxisData,
            showGridWithSpacer = true,
            yAxisRange = 10,
            oneLineChart = false,
            gridOrientation = GridOrientation.HORIZONTAL,
            animateChart = true,
        )
    }
}

