package school

import asset.subMenu.Comparison
import co.touchlab.kermit.Logger
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json

class SchoolAPI {
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
    suspend fun getNoti(school: String, grade: Int, classRoom: Int): List<NotiDetail> {
        try {
            val response: List<NotiItem> = httpClient.get("http://j9c108.p.ssafy.io:8000/member-server/api/notice") {
                url {
                    parameters.append("school", school)
                    parameters.append("grade", "$grade")
                    parameters.append("classroom", "$classRoom")
                }
            }.body<NotiResponse>().body

            val transform: List<NotiDetail> = response.map { noti ->

                val date = "${noti.dateNTime[0]}.${noti.dateNTime[1]}.${noti.dateNTime[2]}"
                val time = "${noti.dateNTime[3]}:${noti.dateNTime[4]}:${noti.dateNTime[5]}"

                NotiDetail(noti.idx, noti.title, date, time)
            }

            return transform
        } catch (e: Exception) {
            println("Error: ${e.message}")
            return emptyList()
        } finally {
            httpClient.close()
        }
    }

    @Throws(Exception::class)
    suspend fun getNotiDetail(id: Int): NoticeDetail {
        try {
            val response: NotiItem = httpClient.get("http://j9c108.p.ssafy.io:8000/member-server/api/notice/$id").body<NotiDetailResponse>().body

            val transform: NoticeDetail = NoticeDetail(response.title, response.content, response.viewCnt)

            return transform
        } catch (e: Exception) {
            println("Error: ${e.message}")
            return NoticeDetail("ERROR", "API연결실패", 0)
        } finally {
            httpClient.close()
        }
    }
}