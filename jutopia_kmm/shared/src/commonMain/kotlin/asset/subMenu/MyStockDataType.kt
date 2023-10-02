package asset.subMenu

enum class Comparison {
    Increased,
    Decreased
}

data class StockDetail(val name: Int, val bought: Int, val diff: Double, val rate: Double, val type: Comparison )