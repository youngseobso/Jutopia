package chatbot

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonNames

@Serializable
data class ChatMessage(
    val message: String,
    @JsonNames("from_server")val fromServer: Boolean
)

@Serializable
data class ChatRequest(
    val message: String
)
