package chatbot


import co.touchlab.kermit.Logger
import co.touchlab.kermit.Message
import io.ktor.client.call.body
import io.ktor.client.statement.bodyAsChannel
import io.ktor.client.statement.bodyAsText
import io.ktor.util.date.GMTDate
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import lease.LeaseApiService
import lease.ListResponse
import lease.Seat
import moe.tlaster.precompose.viewmodel.ViewModel
import moe.tlaster.precompose.viewmodel.viewModelScope
import stock.common.Time
import kotlin.time.TimeSource

private val log = Logger.withTag("chatbot")

sealed class ApiResponse {

}

class ChatbotViewModel : ViewModel() {

    private val _messages = MutableStateFlow<List<ChatMessage>>(emptyList())
    val messages: StateFlow<List<ChatMessage>> = _messages.asStateFlow()
    private val _isSending = MutableStateFlow(false)
    val isSending: StateFlow<Boolean> = _isSending.asStateFlow()

    private val chatApiService: ChatbotApiService = ChatbotApiService()

    //Message를 받아서 넣어준다
    fun addMessage(message: ChatMessage) {
//        _messages.value = _messages.value + message
        ChatRepository.addMessage(message)
        _messages.value = ChatRepository.messages
    }

    suspend fun addAndRespond(message: String) {
        _isSending.value = true
        addMessage(ChatMessage( message, false, ""))
        try {
            val reply = chatApiService.sendMessage(message)
            log.i{
                "응답 : ${reply.bodyAsText()}"
            }
            addMessage(reply.body())
        } catch (e:Exception) {
            log.i{
                "요청 에러 : $e"
            }
        }

        _isSending.value = false
    }

    init {
        val dummyData = listOf(
            ChatMessage( "안녕하세요, 무엇을 도와드릴까요?",  true,""),
        )
//        _messages.value = dummyData
        _messages.value = ChatRepository.messages
    }
}