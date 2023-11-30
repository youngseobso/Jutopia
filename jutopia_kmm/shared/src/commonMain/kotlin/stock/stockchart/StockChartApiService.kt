package stock.stockchart

import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.url
import io.ktor.client.statement.HttpResponse
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

enum class TimeFrame {
    day,
    hour,
    minute
}


class StockChartApiService {

    private companion object {
        const val BASE_URL = "http://j9c108.p.ssafy.io:9001"
        const val JWT_TOKEN = "test" //실제 토큰을 넣어야 하지만 임시로 userId를 넣는
    }
    private val client = HttpClient(CIO) {
        install(ContentNegotiation) {
            json(
                Json { ignoreUnknownKeys = true }
            )
        }
        defaultRequest {
            url(BASE_URL)
//            header("Authorization", "Bearer $JWT_TOKEN")
            header("Authorization", "$JWT_TOKEN")
        }
    }
    private fun HttpRequestBuilder.apiUrl(path: String){
        url("$BASE_URL/$path")
    }

    suspend fun getStockChart(stockCode:String, timeFrame: TimeFrame): HttpResponse {
        return client.get{
            apiUrl("chart/$stockCode/$timeFrame")
        }
    }

}