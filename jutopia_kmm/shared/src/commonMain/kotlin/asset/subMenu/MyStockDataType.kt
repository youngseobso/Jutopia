package asset.subMenu

enum class Comparison {
    Increased,
    Decreased,
    NotChanged,
}

data class StockDetail(val name: String, val bought: Int, val current: Int, val qty: Int, val rate: Double, val changes: Comparison )