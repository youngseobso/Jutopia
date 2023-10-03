package menus

import kotlinx.serialization.Serializable

@Serializable
data class UserInfo(val uuid: String, val id: String)