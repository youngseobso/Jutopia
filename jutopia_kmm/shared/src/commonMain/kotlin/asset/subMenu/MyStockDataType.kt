package asset.subMenu

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

enum class Comparison {
    Increased,
    Decreased,
    NotChanged,
}

data class StockDetail(val name: String, val bought: Int, val current: Int, val qty: Int, val rate: Double, val changes: Comparison )

@Serializable
data class Result(
    @SerialName("resultCode")
    val resultCode: Int,
    @SerialName("resultMessage")
    val resultMessage: String,
    @SerialName("resultDescription")
    val resultDescription: String
)

@Serializable
data class StockItem(
    @SerialName("id")
    val id: String,
    @SerialName("stockName")
    val stockName: String,
    @SerialName("stockCode")
    val stockCode: String,
    @SerialName("nowMoney")
    val nowMoney: Int,
    @SerialName("changeMoney")
    val changeMoney: Int,
    @SerialName("changeRate")
    val changeRate: Double,
    @SerialName("type")
    val type: Int,
    @SerialName("qty")
    val qty: Int,
    @SerialName("totalPrice")
    val totalPrice: Double,
    @SerialName("avgPrice")
    val avgPrice: Double
)

@Serializable
data class ResponseData(
    @SerialName("result")
    val result: Result,
    @SerialName("body")
    val body: List<StockItem>
)