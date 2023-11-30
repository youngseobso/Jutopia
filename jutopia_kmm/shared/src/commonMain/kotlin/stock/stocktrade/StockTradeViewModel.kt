package stock.stocktrade

import UserInfo
import co.touchlab.kermit.Logger
import common.TmpUserInfo
import home.Home
import io.github.xxfast.kstore.KStore
import io.github.xxfast.kstore.file.storeOf
import io.ktor.client.statement.bodyAsText
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import moe.tlaster.precompose.viewmodel.ViewModel
import moe.tlaster.precompose.viewmodel.viewModelScope
import pathTo
import stock.common.StockApiService
import stock.common.MyStock
import stock.common.MyStockResponse
import stock.common.StockRequest
import stock.common.StockTradeResponse

enum class TradeType {
    BUY,
    SELL,
}
enum class StockStatus {
    Contract,
    NonTrading,
}

enum class TradeStatus {
    SUCCESS,
    FAILURE
}


private val log = Logger.withTag("StockAPI")

class StockTradeViewModel(stockId: String) : ViewModel() {
    val store: KStore<UserInfo> = storeOf(filePath = pathTo("user"))
    private val stockId = stockId


    private val _myStock = MutableStateFlow<MyStock?>(null)
    val myStock: StateFlow<MyStock?> = _myStock

    private val _tradeQuantity = MutableStateFlow(0.0)
    var tradeQuantity: StateFlow<Double> =  _tradeQuantity

    private val _tradePrice = MutableStateFlow<Double>(0.0)
    var tradePrice: StateFlow<Double> = _tradePrice

    private val _tradeType = MutableStateFlow<TradeType>(TradeType.BUY)
    var tradeType : StateFlow<TradeType> = _tradeType

    private val _myStockCount = MutableStateFlow<Int>(0)
    var myStockCount: StateFlow<Int> = _myStockCount

    private val _tradeStatus = MutableStateFlow<TradeStatus?>(null)
    val tradeStatus: StateFlow<TradeStatus?> = _tradeStatus

    private val _myPoint = MutableStateFlow<Double>(0.0)
    val myPoint : StateFlow<Double> = _myPoint

    private val stockApiService: StockApiService = StockApiService()
    private val getPoint: Home = Home()
    init {
        getMyStockInfo()
    }



    private fun getMyStockInfo(){

        viewModelScope.launch {
            try {
                val storedUserInfo: UserInfo? = store.get()
                _myPoint.emit(getPoint.getPoint(storedUserInfo!!.id))
                log.i { "주식 id: $stockId" }
                val res = stockApiService.getMyStock(storedUserInfo!!.uuid, stockId = stockId)
                val apiResponse = Json.decodeFromString<MyStockResponse>(res.bodyAsText())
                _myStock.emit(apiResponse.body)
                log.i { "내 주식: ${myStock.value}" }
                _myStockCount.emit(apiResponse.body.qty)
            } catch (e: Exception){
                log.i{"나의 주식 목록 초기화 실패 : ${e}"}
            }
        }
    }


    fun changeTradeType(tradeType: TradeType){
        _tradeType.value = tradeType
    }


    fun tradeStock(stockRequest: StockRequest) {

        viewModelScope.launch {
            try {
                val storedUserInfo: UserInfo? = store.get()

                stockRequest.memberId = storedUserInfo!!.uuid
                log.i{
                    "거래요청 내용 : $stockRequest"
                }
                val response = stockApiService.tradeStock(stockRequest)
                val apiResponse = Json.decodeFromString<StockTradeResponse>(response.bodyAsText())
                log.i {
                    "트레이드 결과: ${apiResponse.body}"
                }
                if (apiResponse.body == null) {
                    _tradeStatus.value = TradeStatus.FAILURE
                } else {
                    _tradeStatus.value = TradeStatus.SUCCESS
                    val storedUserInfo: UserInfo? = store.get()
                    _myPoint.emit(getPoint.getPoint(storedUserInfo!!.id))
                    getMyStockInfo()
                }
            } catch (e: Exception) {
                log.i{"trade 에러 : ${e}"}
                _tradeStatus.value = TradeStatus.FAILURE
            }
        }
    }

    fun resetTradeStatus() {
        _tradeStatus.value = null
    }


}