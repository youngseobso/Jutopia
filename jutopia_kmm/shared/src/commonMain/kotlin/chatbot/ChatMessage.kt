package chatbot

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonNames

@Serializable
data class ChatMessage(
//    val userId: String = "",
    val message: String,
    @JsonNames("from_server")val fromServer: Boolean,
    @JsonNames("parsed_time")val parsedTime: String
)

@Serializable
data class ChatRequest(
//    val userId: String,
    val message: String,
//    @JsonNames("from_server")val fromServer: Boolean = false,
)
