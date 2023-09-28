package chatbot

data class ChatMessage(
    val id: String,
    val content: String,
    val timestamp: String,
    val isFromBot: Boolean
)
