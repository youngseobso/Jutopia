package asset.subMenu

import co.touchlab.kermit.Logger
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json

class MyStockAPI {
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
    suspend fun getMyStock(userUUID: String): List<StockDetail> {
        val log = Logger.withTag("stockApi")

        log.d("start")

        try {
            val response: ResponseData = httpClient.get("http://j9c108.p.ssafy.io:8000/stock-server/api/memberstock/${userUUID}").body<ResponseData>()

            val transform: List<StockDetail> = response.body.map { si ->
                StockDetail(si.stockName, si.nowMoney + si.changeMoney * si.type * -1, si.nowMoney, si.qty, si.changeRate, if(si.type == 1) Comparison.Increased else if (si.type == 0) Comparison.NotChanged else Comparison.Decreased)
            }

            log.d("stock detail : ${transform}")

            return transform

        } catch (e: Exception) {
            log.d("Error: ${e.message}")
            return emptyList()
        } finally {
            httpClient.close()
        }
    }
}