package chatbot

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonNames

@Serializable
data class ChatMessage(
    val message: String,
    @JsonNames("from_server")val fromServer: Boolean,
    @JsonNames("parsed_time")val parsedTime: String
)

@Serializable
data class ChatRequest(
    val user_id: String,
    val message: String,
)
