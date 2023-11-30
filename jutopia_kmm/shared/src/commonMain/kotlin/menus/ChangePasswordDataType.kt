package menus

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class ResultInfo(
    @SerialName("student_update_result")
    val updateResult: Boolean
)

@Serializable
data class ChangeResponseData(
    @SerialName("body")
    val body: ResultInfo
)

@Serializable
data class ChangeRequest(
    @SerialName("student_id")
    val id: String,
    @SerialName("student_pwd")
    val password: String,
    @SerialName("student_new_pwd")
    val newPassword: String
)