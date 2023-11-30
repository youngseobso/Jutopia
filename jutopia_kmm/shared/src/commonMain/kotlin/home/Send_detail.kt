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
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
import io.ktor.http.encodeURLQueryComponent
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

private val log = Logger.withTag("Send_detail")
val Gray = Color(0xFFB1B6B9)
val Navy = Color(0xFF3F51B5)


class Send_detailAPI {
    private val client = HttpClient(CIO) {
        install(ContentNegotiation){
            json(
                Json { ignoreUnknownKeys = true }
            )
        }
    }

    @OptIn(InternalAPI::class)
    suspend fun moneySend(sender: String, receiver: String, amount: String, student_id: String) {

        val requestBody = mapOf(
            "sender" to sender,
            "receiver" to receiver,
            "amount" to amount,
            "student_id" to student_id,
        )

        try {
            val response: HttpResponse = client.post("http://j9c108.p.ssafy.io:8000/member-server/api/account/send") {
                contentType(ContentType.Application.Json)
                body = Json.encodeToString(requestBody)
            }
            log.i {"$response"}
        } catch (e: Exception) {
            log.e(e) { "송금에러" }
        }
    }
}


@OptIn(ExperimentalResourceApi::class)
@Composable
fun Send_detail(navigator: Navigator, studentName: String, studentNumber: Int) {
    var money by remember { mutableStateOf("") }
    var checkHuman = "drawable/checkHuman.xml"
    val checkHumanIcon: Painter = painterResource(checkHuman)
    val coroutineScope = rememberCoroutineScope()
    var mybalance by remember { mutableStateOf(0.0) }
    var id by remember { mutableStateOf("") }
    var showConfirmationDialog by remember { mutableStateOf(false) }

    val store: KStore<UserInfo> = storeOf(filePath = pathTo("user"))
    LaunchedEffect(1) {
        val temp: UserInfo? = store.get()
        if (temp != null) {
            id = temp.id
        }
    }

    coroutineScope.launch {
        val account = MyAccountAPI().getAccountInfo(id)
        mybalance = account.balance
    }

    fun formatThousandSeparator(number: Int?): String? {
        return number?.let {
            it.toString().reversed().chunked(3).joinToString(",").reversed()
        }
    }

    val formattedBalance = formatThousandSeparator(mybalance.toInt())



    Column {
        TopPageBar("송금하기", navigator=navigator)
        Column(
            modifier = Modifier
                .padding(start = 30.dp)
        ) {
            Box {
                Column {
                    Text("나의 계좌에서... 에서")
                    Text("잔액 $${formattedBalance}", fontSize = 15.sp, color = Gray)
                }
            }
            Spacer(modifier = Modifier.height(30.dp))
            Row {
                Image(
                    painter = checkHumanIcon,
                    contentDescription = "checkHuman Icon",
                    modifier = Modifier.offset(y = 10.dp)
                )
                Spacer(modifier = Modifier.width(10.dp))
                Column {
                    Text("$studentName")
                    Text("번호: $studentNumber", color = Gray)
                }
            }
        }
        Spacer(modifier = Modifier.height(30.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextField(
                modifier = Modifier.fillMaxHeight(),
                value = money,
                onValueChange = { if (it.all { char -> char.isDigit() }) money = it }, // 숫자만 허용
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number), // 숫자 키보드 사용
                shape = RoundedCornerShape(16.dp),
                textStyle= TextStyle(fontSize=25.sp),
                placeholder = { Text("얼마를 보낼까요?", fontSize = 20.sp) },
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.White,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                )
            )

            Box(
                modifier = Modifier
                    .width(50.dp)
                    .height(35.dp)
                    .background(color = Navy)
                    .clickable {
                        coroutineScope.launch {
                            Send_detailAPI().moneySend(id!!, studentName!!, money!!, id!!)
                            showConfirmationDialog = true
                        }
                    },
                contentAlignment = Alignment.Center
            ) {
                Text("송금", color = Color.White)
            }
        }
        if (showConfirmationDialog) {
            AlertDialog(
                onDismissRequest = { showConfirmationDialog = false },
                title = { Text("송금 완료") },
                text = { Text("송금이 성공적으로 완료되었습니다.") },
                confirmButton = {
                    Button(onClick= {
                        showConfirmationDialog= false
                        navigator.navigate("/asset")  // Navigate to "/asset" page.
                    }) {
                        Text("확인")
                    }
                }
            )
        }
    }


    BottomTabBar(navigator, 0)
}