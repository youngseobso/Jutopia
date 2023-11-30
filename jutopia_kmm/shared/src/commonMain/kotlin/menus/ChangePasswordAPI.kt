package menus

import co.touchlab.kermit.Logger
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

class ChangePasswordAPI {
    private  val httpClient = HttpClient {
        install(ContentNegotiation) {
            json(Json {
                prettyPrint = true
                isLenient = true
                ignoreUnknownKeys = true
            })
        }
    }

    @Throws(Exception::class)
    suspend fun changePwd(id: String, pwd: String, newPwd: String): Boolean {

        val log = Logger.withTag("changeAPI")

        log.d("id: $id, pwd: $pwd, newpwd: $newPwd")

        try {
            val response: ChangeResponseData = httpClient.put("http://j9c108.p.ssafy.io:8000/member-server/api/student/update") {
                contentType(ContentType.Application.Json)
                setBody(ChangeRequest(id, pwd, newPwd))
            }.body<ChangeResponseData>()

            log.d("${response}")

            return response.body.updateResult

        } catch (e: Exception) {
            println("Error: ${e.message}")
            return false
        } finally {
            httpClient.close()
        }
    }
}