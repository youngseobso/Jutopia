package chatbot

object ChatRepository {
    private val  _message = mutableListOf<ChatMessage>(
        ChatMessage( message = "안녕하세요, 무엇을 도와드릴까요?",  fromServer = true, parsedTime = "")
    )
    val messages: List<ChatMessage> get() = _message.toList()

    fun addMessage(message: ChatMessage) {
        _message.add(message)
    }

    fun clearMessages(){
        _message.clear()
    }
}