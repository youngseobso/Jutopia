package chatbot

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonNames

@Serializable
data class ChatMessage(
    val message: String,
    @SerialName("from_server")val fromServer: Boolean,
    @SerialName("parsed_time")val parsedTime: String
)

@Serializable
data class ChatRequest(
    val message: String,
)
