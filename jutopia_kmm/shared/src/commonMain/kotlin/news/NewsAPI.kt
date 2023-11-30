package news

import co.touchlab.kermit.Logger
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

class NewsAPI {
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
    suspend fun getNewses(brand: String): List<NewsDetail> {
        try {
            val response: ResponseNewsData = httpClient.get("http://j9c108.p.ssafy.io:8000/news-server/naver/${brand}/1/20").body<ResponseNewsData>()

            val monthToNum = mapOf<String, String>("Jan" to "01", "Feb" to "02", "Mar" to "03", "Apr" to "04", "May" to "05", "Jun" to "06", "Jul" to "07", "Aug" to "08", "Sep" to "09", "Oct" to "10", "Nov" to "11", "Dec" to "12")

            val transform: List<NewsDetail> = response.body.map { news ->
                val year = news.pubDate.slice(IntRange(12,15))
                val month = monthToNum[news.pubDate.slice(IntRange(8,10))]
                val date = news.pubDate.slice(IntRange(5,6))
                val time = news.pubDate.slice(IntRange(17,24))
                NewsDetail(news.title, news.description, "네이버 뉴스", "$year.$month.$date", time, news.link)
            }


            return transform

        } catch (e: Exception) {
            println("Error: ${e.message}")
            return emptyList()
        } finally {
            httpClient.close()
        }
    }

    val summaryClient = HttpClient(CIO) {
        install(ContentNegotiation) {
            json(Json {
                prettyPrint = true
                isLenient = true
                ignoreUnknownKeys = true
            })
        }
        install(HttpTimeout) {
            requestTimeoutMillis = 100000 // 요청에 대한 timeout (밀리초)
            connectTimeoutMillis = 50000 // 연결에 대한 timeout (밀리초)
            socketTimeoutMillis = 50000 // 소켓에 대한 timeout (밀리초)
        }
    }

    @Throws(Exception::class)
    suspend fun getSummary(link: String): String {
        try {
            val response: String = summaryClient.post("http://j9c108.p.ssafy.io:9002/sumnews"){
                contentType(ContentType.Application.Json)
                setBody(RequestSummary(link))
            }.body<String>()

            return response

        } catch (e: Exception) {
            return "Error: ${e.message}"
        } finally {
            httpClient.close()
        }
    }
}