package lease

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

//data class Seat(val id: Int, val info: String, val price: Int, val isAvailable: Boolean)


@Serializable
data class ListResponse(
    val result: ApiResult,
    val body: List<Seat>?
)

@Serializable
data class Response(
    val result: ApiResult,
    val body: Seat?
)


@Serializable
data class ApiResult(
    @SerialName("result_code") val resultCode: Int,
    @SerialName("result_message") val resultMessage: String,
    @SerialName("result_description") val resultDescription: String
)
@Serializable
data class Seat(
    val id: String,
    val position: Int,
    val price: Double,
    @SerialName("user_id") val userId: String?,
    @SerialName("seat_status") val seatStatus: String,
    @SerialName("clazz_number") val clazzNumber: Int,
    val grade: Int,
    val school: String,
)

@Serializable
data class SeatRequest(
    @SerialName("seat_id")val seatId: String,
    @SerialName("user_id")val userId: String,
)