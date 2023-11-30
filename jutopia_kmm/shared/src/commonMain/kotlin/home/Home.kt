@file:OptIn(ExperimentalResourceApi::class)

package home

import BottomTabBar
import UserInfo
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp
import asset.subMenu.MyAccountAPI
import co.touchlab.kermit.Logger
import common.TopPageBar
import io.github.xxfast.kstore.KStore
import io.github.xxfast.kstore.file.storeOf
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import io.ktor.util.InternalAPI
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import moe.tlaster.precompose.navigation.Navigator
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import pathTo

private val log = Logger.withTag("Home")
val LightGray = Color(0xFFF6F6F6)
val startColor = Color(0xFF8FE0FF)

@Serializable
data class PointResponse(
    val api_status: String,
    val message: String?,
    val data: Double
)


class Home {
    private val client = HttpClient(CIO) {
        install(ContentNegotiation) {
            json(
                Json { ignoreUnknownKeys = true }
            )
        }
    }

    suspend fun getHome(): String {
        val response: HttpResponse =
            client.get("http://j9c108.p.ssafy.io:8000/class-server/api/school/")
        val body: String = response.bodyAsText()
        return body
    }

    suspend fun getPoint(studentId: String): Double {

        val response: HttpResponse = client.get("http://j9c108.p.ssafy.io:8000/member-server/api/pointtransaction/point/student?studentId=$studentId")
        val body: String = response.bodyAsText()
        val json = Json { ignoreUnknownKeys = true }
        val pointResponse = json.decodeFromString<PointResponse>(body)

        return pointResponse.data
    }
}





@OptIn(ExperimentalResourceApi::class)
@Composable
fun Home(navigator: Navigator) {

    fun formatThousandSeparator(number: Int?): String? {
        return number?.let {
            it.toString().reversed().chunked(3).joinToString(",").reversed()
        }
    }

    val coroutineScope = rememberCoroutineScope()
    var myaccount by remember { mutableStateOf("") }
    var mybalance by remember { mutableStateOf(0.0) }
    var myPoint by remember { mutableStateOf(0.0) }
    var id by remember { mutableStateOf("") }

    coroutineScope.launch {
        var test = Home()
        test.getHome()
        val account = MyAccountAPI().getAccountInfo(id)
        myPoint = Home().getPoint(id)
        myaccount = account.number
        mybalance = account.balance
        log.i { "$account" }
    }

    val store: KStore<UserInfo> = storeOf(filePath = pathTo("user"))
    LaunchedEffect(1) {
        val temp: UserInfo? = store.get()
        if (temp != null) {
            id = temp.id
        }
    }

    var bankImg = "drawable/bank.xml"
    var stockImg = "drawable/stock.xml"
    var rentImg = "drawable/rent.xml"
    var tradeImg = "drawable/trade.xml"
    var marketImg = "drawable/market.xml"
    var noticeImg = "drawable/notice.xml"


    val bankIcon: Painter = painterResource(bankImg)
    val stockIcon: Painter = painterResource(stockImg)
    val rentIcon: Painter = painterResource(rentImg)
    val tradeIcon: Painter = painterResource(tradeImg)
    val marketIcon: Painter = painterResource(marketImg)
    val noticeIcon: Painter = painterResource(noticeImg)

    var selectedTab by remember { mutableStateOf(0) }

    val formattedBalance = formatThousandSeparator(mybalance.toInt())

    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TopPageBar("홈", navigator, showReturn = false)
        Box(
            modifier = Modifier
                .width(250.dp)
                .height(200.dp)
                .background(LightGray),
        ) {
            Column(
                modifier = Modifier
                    .align(Alignment.Center),
                verticalArrangement= Arrangement.spacedBy(10.dp),
                horizontalAlignment = Alignment.CenterHorizontally

            ) {
                Text("나의 계좌번호: ${myaccount}")
                Text("나의 화폐: ${formattedBalance}")
                Text("나의 포인트: $myPoint")
                Box(
                    modifier = Modifier
                        .width(200.dp)
                        .height(1.dp)
                        .background(Color.Black)
                        .align(Alignment.CenterHorizontally),
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Box(
                        modifier = Modifier
                            .clickable { selectedTab = 8 }
                    ) {
                        Text("거래내역")
                    }
                    Spacer(modifier = Modifier.width(20.dp)) // 이 부분 추가
                    Box(
                        modifier = Modifier
                            .width(2.dp)
                            .height(25.dp)
                            .background(Color.Black)
                    )
                    Spacer(modifier = Modifier.width(20.dp)) // 이 부분 추가
                    Box(
                        modifier = Modifier
                            .clickable { selectedTab = 7 }
                    ) {
                        Text("송금하기")
                    }
                }
            }
        }

        Row(
            modifier = Modifier
                .width(250.dp)
                .height(100.dp)
                .background(Color.White)
                .padding(top = 16.dp),
//            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly,
        ) {
            // 버튼 1
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .width(60.dp)
                        .height(60.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(LightGray)
                        .clickable { selectedTab = 1 },
                    contentAlignment = Alignment.Center
                ) {
                    Image(painter = bankIcon, contentDescription = "Bank Icon")
                }
                Text("은행")
            }

            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .width(60.dp)
                        .height(60.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(LightGray)
                        .clickable { selectedTab = 2 },
                    contentAlignment = Alignment.Center
                ) {
                    Image(painter = stockIcon, contentDescription = "Stock Icon")
                }
                Text("주식")
            }

            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .width(60.dp)
                        .height(60.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(LightGray)
                        .clickable { selectedTab = 3 },
                    contentAlignment = Alignment.Center
                ) {
                    Image(painter = rentIcon, contentDescription = "Rent Icon")
                }
                Text("임대")
            }
        }
        Row(
            modifier = Modifier
                .width(250.dp)
                .height(100.dp)
                .background(Color.White)
                .padding(top = 16.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
        ) {

            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .width(60.dp)
                        .height(60.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(LightGray)
                        .clickable { selectedTab = 4 },
                    contentAlignment = Alignment.Center
                ) {
                    Image(painter = tradeIcon, contentDescription = "Trade Icon")
                }
                Text("환전")
            }

            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .width(60.dp)
                        .height(60.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(LightGray)
                        .clickable { selectedTab = 5 },
                    contentAlignment = Alignment.Center
                ) {
                    Image(painter = marketIcon, contentDescription = "Market Icon")
                }
                Text("상점")
            }

            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .width(60.dp)
                        .height(60.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(LightGray)
                        .clickable { selectedTab = 6 },
                    contentAlignment = Alignment.Center
                ) {
                    Image(painter = noticeIcon, contentDescription = "Notice Icon")
                }
                Text("공지사항")
            }
        }
        when (selectedTab) {
            1 -> {
                navigator.navigate("/bank")
            }

            2 -> {
                navigator.navigate("/stocklist")
            }

            3 -> {
                navigator.navigate("/rent")
            }

            4 -> {
                navigator.navigate("/trade")
            }

            5 -> {
                navigator.navigate("/market")
            }

            6 -> {
                navigator.navigate("/notice")
            }

            7 -> {
                navigator.navigate("/send")
            }

            8 -> {
                navigator.navigate("/asset")
            }

        }

        BottomTabBar(navigator, 0)
    }
}