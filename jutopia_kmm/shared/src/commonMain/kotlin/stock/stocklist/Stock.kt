package stock.stocklist

import stock.stocktrade.StockStatus
import stock.stocktrade.TradeType

data class Stock(
    val id: String,
    val name: String,
    val price: Double,
    val changePercent: Double,
    val isOwnedByUser: Boolean,
    val changeAmount: Double,
    val status: StockStatus?,
    val type: TradeType?
)
