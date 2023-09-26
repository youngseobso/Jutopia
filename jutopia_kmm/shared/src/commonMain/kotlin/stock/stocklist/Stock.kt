package stock.stocklist

data class Stock(
    val id: String,
    val name: String,
    val price: Double,
    val changePercent: Double,
    val isOwnedByUser: Boolean,
    val changeAmount: Double,
)
