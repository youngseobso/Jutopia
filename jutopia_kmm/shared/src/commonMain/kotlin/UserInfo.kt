import kotlinx.serialization.Serializable

@Serializable
data class UserInfo(val uuid: String, val id: String, val school: String, val grade: Int, val classroom: Int, val studentNumber: Int)