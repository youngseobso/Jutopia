package asset.subMenu

import co.touchlab.kermit.Logger
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

class MyAccountAPI {
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
    suspend fun getAccountInfo(studentId:String ): AccountInformation {


        try {
            val response: AccountResponseData = httpClient.get("http://j9c108.p.ssafy.io:8000/member-server/api/account") {
                url {
                    parameters.append("studentId", studentId)
                }
            }.body<AccountResponseData>()

            val transform: AccountInformation = AccountInformation(response.body.uuid, response.body.bank, response.body.number, response.body.balance)

            return transform

        } catch (e: Exception) {
            println("Error: ${e.message}")
            return AccountInformation("error", "", "", 0.0)
        } finally {
            httpClient.close()
        }
    }

    @Throws(Exception::class)
    suspend fun getAccoutHistory(uuid: String): List<DepositDetail> {
        try {
            val response: AccountHistoryResponseData = httpClient.get("http://j9c108.p.ssafy.io:8000/member-server/api/history") {
                url {
                    parameters.append("accountId", uuid)
                }
            }.body<AccountHistoryResponseData>()

            val transform: List<DepositDetail> = response.body.map { history ->
                DepositDetail("${history.time[0]}.${history.time[1]}.${history.time[2]}", "${history.time[3]}:${history.time[4]}:${history.time[5]}", if(history.transactionType == "INCOME") TransactionType.Deposit else TransactionType.Withdrawal, if(history.transactionType == "INCOME") "받기 | ${history.sender}" else "송금 | ${history.receiver}", history.amount, history.changes)
            }

            Logger.d("내역 어디감? ${response.body}")

            return transform

        } catch (e: Exception) {
            println("Error: ${e.message}")
            return emptyList()
        } finally {
            httpClient.close()
        }
    }
}