package stock.stocklist


import UserInfo
import co.touchlab.kermit.Logger
import common.TmpUserInfo
import io.github.xxfast.kstore.KStore
import io.github.xxfast.kstore.file.storeOf
import io.ktor.client.statement.bodyAsText
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import moe.tlaster.precompose.viewmodel.ViewModel
import moe.tlaster.precompose.viewmodel.viewModelScope
import pathTo
import stock.common.Stock
import stock.common.StockApiService
import stock.common.StockListResponse
import kotlin.random.Random

private val log = Logger.withTag("StockAPI")

class StockListViewModel : ViewModel() {
    private val _stocks = MutableStateFlow<List<Stock>>(listOf())

    val stocks: StateFlow<List<Stock>> = _stocks

    private val stockApiService = StockApiService();
    val store: KStore<UserInfo> = storeOf(filePath = pathTo("user"))

    init {
        viewModelScope.launch {
            try {
                val storedUserInfo: UserInfo? = store.get()
                while (true) {
                    val response = stockApiService.getAllStocks(storedUserInfo!!.uuid);
                    val apiResponse = Json.decodeFromString<StockListResponse>(response.bodyAsText())
                    val stockList = apiResponse.body
                    _stocks.emit(stockList!!)
                    delay(10000)
                }
            } catch (e: Exception) {
                log.i { "주식 리스트 에러: ${e}" }
            }
        }
    }
}