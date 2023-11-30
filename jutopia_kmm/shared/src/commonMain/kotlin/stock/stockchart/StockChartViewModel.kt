package stock.stockchart

import co.touchlab.kermit.Logger
import io.ktor.client.statement.bodyAsText
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import moe.tlaster.precompose.viewmodel.ViewModel
import moe.tlaster.precompose.viewmodel.viewModelScope
import stock.common.StockChart

private val log = Logger.withTag("stockChart")

class StockChartViewModel(stockId: String, stockCode: String) : ViewModel() {
    private val _minuteChartData = MutableStateFlow<List<Pair<String, Double?>>>(emptyList())
    val minuteChartData: StateFlow<List<Pair<String, Double?>>> = _minuteChartData

    private val _hourChartData = MutableStateFlow<List<Pair<String, Double?>>>(emptyList())
    val hourChartData: StateFlow<List<Pair<String, Double?>>> = _hourChartData

    private val _dayChartData = MutableStateFlow<List<Pair<String, Double?>>>(emptyList())
    val dayChartData: StateFlow<List<Pair<String, Double?>>> = _dayChartData

    private val _currentChartData = MutableStateFlow<List<Pair<String, Double?>>>(emptyList())
    val currentChartData: StateFlow<List<Pair<String, Double?>>> = _currentChartData

    private val _isLoading = MutableStateFlow<Boolean>(true)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _timeFrame = MutableStateFlow(TimeFrame.day)
    val timeFrame: StateFlow<TimeFrame> = _timeFrame

    private val apiService = StockChartApiService()

    fun setTimeFrame(timeFrame: TimeFrame) {
        when (timeFrame) {
            TimeFrame.minute -> {
                _currentChartData.value = _minuteChartData.value
                _timeFrame.value = TimeFrame.minute
            }

            TimeFrame.hour -> {
                _currentChartData.value = _hourChartData.value
                _timeFrame.value = TimeFrame.hour
            }

            TimeFrame.day -> {
                _currentChartData.value = _dayChartData.value
                _timeFrame.value = TimeFrame.day
            }
        }
    }

    private suspend fun getChartData(timeFrame: TimeFrame, stockCode: String) {
        try {
            val res = apiService.getStockChart(stockCode, timeFrame)
            val jsonRes = Json.decodeFromString<StockChart>(res.bodyAsText())
            val priceMapData = jsonRes.price
            val convertedData = priceMapData.map { (key, value) ->
                if (value == null) {
                    Pair(key, null)
                } else {
                    value.replace(",", "").toDoubleOrNull()?.let {
                        Pair(key, it)
                    } ?: Pair(key, null)
                }
            }

            when (timeFrame) {
                TimeFrame.minute -> _minuteChartData.emit(convertedData)
                TimeFrame.hour -> _hourChartData.emit(convertedData)
                TimeFrame.day -> _dayChartData.emit(convertedData)
            }
        } catch (e: Exception) {
            log.i { "차트 코드 : ${stockCode} 차트 데이터 에러 : ${e}" }
        }
    }


    init {
        viewModelScope.launch {
            _isLoading.emit(true)
            val minuteData = async { getChartData(TimeFrame.minute, stockCode) }
            val hourData = async { getChartData(TimeFrame.hour, stockCode) }
            val dayData = async { getChartData(TimeFrame.day, stockCode) }
            try {
                awaitAll(minuteData, hourData, dayData)
                log.i { "분 데이터: ${minuteChartData.value}" }
                log.i { "시 데이터: ${hourChartData.value}" }
                log.i { "일 데이터: ${dayChartData.value}" }
            } catch (e: Exception) {
                log.i { "비동기 처리 에러 : $e" }
            } finally {
                log.i { "finally 진입" }
                _currentChartData.value = _minuteChartData.value
                _isLoading.emit(false)
            }
        }

        viewModelScope.launch {
            while (true) {
                delay(10_000)
                log.i { "분 데이터 갱신" }
                getChartData(TimeFrame.minute, stockCode)
                log.i { "현제 프렝미: ${_timeFrame.value}" }
                if (_timeFrame.value == TimeFrame.minute) {
                    _currentChartData.value = _minuteChartData.value
                }
            }
        }

        viewModelScope.launch {
            while (true) {
                delay(30_000)
                log.i { "시 데이터 갱신" }
                getChartData(TimeFrame.hour, stockCode)
                if (_timeFrame.value == TimeFrame.hour) {
                    _currentChartData.value = _hourChartData.value
                }
            }
        }

        viewModelScope.launch {
            while (true) {
                delay(30_000)
                getChartData(TimeFrame.day, stockCode)
                log.i { "일 데이터 갱신" }
                if (_timeFrame.value == TimeFrame.day) {
                    _currentChartData.value = _dayChartData.value
                }
            }
        }

    }
}



