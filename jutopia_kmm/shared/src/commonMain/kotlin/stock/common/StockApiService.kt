package stock.common

import co.touchlab.kermit.Logger
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.url
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType
import io.ktor.serialization.kotlinx.json.json
import io.ktor.util.InternalAPI
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

private val log = Logger.withTag("StockAPI")
class StockApiService {

    private companion object {
        const val BASE_URL = "http://j9c108.p.ssafy.io:8000/stock-server/api"
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

    suspend fun getAllStocks(memberId: String): HttpResponse{
        return client.get{
            apiUrl("stock/stocklist/${memberId}")
        }
    }

    suspend fun getStock(stockId: String): HttpResponse {
        return client.get{
            apiUrl("stock/$stockId")
        }
    }

    suspend fun getMyAllStocks(memberId: String): HttpResponse {
        return client.get{
            apiUrl("memberstock/$memberId")
        }
    }

    suspend fun getMyStock(memberId: String, stockId: String): HttpResponse {
        return client.get{
            apiUrl("memberstock/$memberId/$stockId")
        }
    }
    @OptIn(InternalAPI::class)
    suspend fun tradeStock(stockRequest: StockRequest): HttpResponse {
        val jsonData = Json.encodeToString(stockRequest)
        return client.post {
            apiUrl("trade/")
            header("Content-Type", ContentType.Application.Json.toString())
            body = jsonData
        }
    }





}