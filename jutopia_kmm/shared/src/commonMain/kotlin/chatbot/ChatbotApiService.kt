package chatbot

import co.touchlab.kermit.Logger
import common.TmpUserInfo
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.url
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType
import io.ktor.serialization.kotlinx.json.json
import io.ktor.util.InternalAPI
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class ChatbotApiService {
    private val log = Logger.withTag("chatbot")
    //TODO: 기존 질문에 이어서 질문할 수 있도록 수정 필요
    private companion object {
        const val BASE_URL = "http://j9c108.p.ssafy.io:8000/chat-server"
    }
    private val client = HttpClient(CIO) {
        install(ContentNegotiation) {
            json(
                Json { ignoreUnknownKeys = true }
            )
        }
        defaultRequest {
            url(BASE_URL)
        }
    }
    private fun HttpRequestBuilder.apiUrl(path: String){
        url("$BASE_URL/$path")
    }


    @OptIn(InternalAPI::class)
    suspend fun sendMessage(inputMessage: String): HttpResponse {
        val memberId = TmpUserInfo.getMemberId()
        val request: ChatRequest = ChatRequest(inputMessage)
        val jsonData = Json.encodeToString(request);
        log.i { "요청: ${jsonData}" }
        return client.post{
            apiUrl("ask")
            header("Content-Type", ContentType.Application.Json.toString())

            body = jsonData
        }
    }
}