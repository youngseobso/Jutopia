package stock.common

import co.touchlab.kermit.Logger
import io.ktor.client.statement.bodyAsText
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import moe.tlaster.precompose.viewmodel.ViewModel
import moe.tlaster.precompose.viewmodel.viewModelScope


enum class PageType {
    CHART,
    TRADE
}

enum class StockLoadingState {
    LOADING,
    LOADED,
    ERROR
}

private val log = Logger.withTag("StockAPI")

class StockViewModel(stockId: String, stockCode: String) :ViewModel() {
    private val _currentPage = MutableStateFlow(PageType.CHART)
    val currentPage: StateFlow<PageType> = _currentPage

    private val _currentStock = MutableStateFlow<Stock?>(null)
    val currentStock = _currentStock

    private val _loadingState = MutableStateFlow(StockLoadingState.LOADING)
    val loadingState: StateFlow<StockLoadingState> = _loadingState


    private val stockApiService = StockApiService()
    init {
        viewModelScope.launch {
            try {
                _loadingState.emit(StockLoadingState.LOADING)
                val response = stockApiService.getStock(stockId)
                val apiResponse = Json.decodeFromString<StockResponse>(response.bodyAsText())
                _currentStock.emit(apiResponse.body!!)
                _loadingState.emit(StockLoadingState.LOADED)
            } catch (e: Exception){
                log.i{"stockViewModel 에러 : $e"}
                _loadingState.emit(StockLoadingState.ERROR)
            }
        }
    }

    fun changePage(newPage: PageType) {
        _currentPage.value = newPage
    }
}