package Sign

import co.touchlab.kermit.Logger
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.appendPathSegments
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

class SignAPI {
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
    suspend fun login(id: String, pwd: String): LoginResponseData? {

        val log = Logger.withTag("loginAPI")


        try {
            val response: LoginResponseData = httpClient.post("http://j9c108.p.ssafy.io:8000/member-server/api/sign-in") {
                contentType(ContentType.Application.Json)
                setBody(LoginRequest(id, pwd))
            }.body<LoginResponseData>()

            return response

        } catch (e: Exception) {
            println("Error: ${e.message}")
            return null
        } finally {
            httpClient.close()
        }
    }

    private  val nonSerializeHttpClient = HttpClient()
    @Throws(Exception::class)
    suspend fun getClassUUID(school: String, grade: Int, classroom: Int): String? {

        val log = Logger.withTag("loginAPI")

        try {
            val response: String = nonSerializeHttpClient.get("http://j9c108.p.ssafy.io:8000/class-server/api/school") {
                url {
                    appendPathSegments(school, "$grade", "$classroom")
                }
            }.body<String>()

            return response.removeSurrounding("\"")

        } catch (e: Exception) {
            println("Error: ${e.message}")
            return null
        } finally {
            httpClient.close()
        }
    }
}