package stock.stocklist

data class Stock(
    val name: String,
    val price: Double,
    val changePercent: Double,
    val isOwnedByUser: Boolean
)
