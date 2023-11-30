package Sign

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LoginInfo(
    @SerialName("id")
    val uuid: String,
    @SerialName("member_id")
    val id: String,
    @SerialName("name")
    val name: String,
    @SerialName("school")
    val school: String,
    @SerialName("grade")
    val grade: Int,
    @SerialName("classroom")
    val classroom: Int,
    @SerialName("student_number")
    val studentNumber: Int
)

@Serializable
data class LoginResult(
    @SerialName("result_code")
    val code: Int
)

@Serializable
data class LoginResponseData(
    @SerialName("result")
    val result: LoginResult,
    @SerialName("body")
    val body: LoginInfo
)

@Serializable
data class LoginRequest(
    @SerialName("member_id")
    val id: String,
    @SerialName("member_pwd")
    val password: String
)