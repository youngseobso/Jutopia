package home

import BottomTabBar
import UserInfo
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
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
import io.ktor.client.request.post
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import io.ktor.util.InternalAPI
import kotlinx.coroutines.launch
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import moe.tlaster.precompose.navigation.Navigator
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import pathTo

private val log = Logger.withTag("Trade")
class TradedAPI {
    private val client = HttpClient(CIO) {
        install(ContentNegotiation){
            json(
                Json { ignoreUnknownKeys = true }
            )
        }
    }
    @OptIn(InternalAPI::class)
    suspend fun moneyToPoint(student_id: String,income: String) {

        val requestBody = mapOf(
            "student_id" to student_id,
            "income" to income,
        )

        try {
            val response: HttpResponse = client.post("http://j9c108.p.ssafy.io:8000/member-server/api/pointtransaction/income") {
                contentType(ContentType.Application.Json)
                body = Json.encodeToString(requestBody)
            }
            log.i {"$response"}
        } catch (e: Exception) {
            log.e(e) { "환전 에러" }
        }
    }

    @OptIn(InternalAPI::class)
    suspend fun pointToMoney(student_id: String, expense: String) {

        val requestBody = mapOf(
            "student_id" to student_id,
            "expense" to expense,
        )

        try {
            val response: HttpResponse = client.post("http://j9c108.p.ssafy.io:8000/member-server/api/pointtransaction/expense") {
                contentType(ContentType.Application.Json)
                body = Json.encodeToString(requestBody)
            }
            log.i {"$response"}
        } catch (e: Exception) {
            log.e(e) { "환전 에러" }
        }
    }
}

val lightblue = Color(0xFFE7EFFA)
val Blue = Color(0xFF3F51B5)
val deepgray = Color(0xFF4D545C)
val DeepBlue = Color(0xFF3F51B5)
@OptIn(ExperimentalResourceApi::class)
@Composable
fun Trade(navigator: Navigator) {
    var showConfirmationDialog by remember { mutableStateOf(false) }
    var money by remember { mutableStateOf("") }
    var arrowImg = "drawable/arrow.xml"
    var bigarrowImg = "drawable/bigarrow.xml"

    val arrowIcon: Painter = painterResource(arrowImg)
    val bigarrowIcon: Painter = painterResource(bigarrowImg)

    var expanded by remember { mutableStateOf(false) }
    val items = listOf("달러", "포인트")
    var selectedCurrency by remember { mutableStateOf(items[0]) }
    val unselectedItem by derivedStateOf { items.first { it != selectedCurrency } }
    var id by remember { mutableStateOf("") }
    val coroutineScope = rememberCoroutineScope()
    var mybalance by remember { mutableStateOf(0.0) }

    val store: KStore<UserInfo> = storeOf(filePath = pathTo("user"))
    LaunchedEffect(1) {
        val temp: UserInfo? = store.get()
        if (temp != null) {
            id = temp.id
        }

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
        TopPageBar("환전", navigator=navigator)
        Box(
            modifier = Modifier
                .fillMaxWidth(),
            contentAlignment = Alignment.Center

        ) {
            Box(
                modifier = Modifier
                    .width(300.dp)
                    .height(130.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(lightblue),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Row {
                        Spacer(modifier = Modifier.width(5.dp))
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Box(
                                modifier = Modifier
                                    .width(50.dp)
                                    .clip(RoundedCornerShape(10.dp))
                                    .background(Blue),
                                contentAlignment = Alignment.Center

                            ) {
                                Text("USD")
                            }
                            Text("1,333.25")
                            Text("▼4.00(0.29%)", fontSize = 12.sp, color = Color.Blue)
                        }
                        Spacer(modifier = Modifier.width(5.dp))
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Box(
                                modifier = Modifier
                                    .width(50.dp)
                                    .clip(RoundedCornerShape(10.dp))
                                    .background(Blue),
                                contentAlignment = Alignment.Center
                            ) {
                                Text("JPY")
                            }
                            Text("906.75")
                            Text("▼0.93(0.10%)", fontSize = 12.sp, color = Color.Blue)
                        }
                        Spacer(modifier = Modifier.width(5.dp))
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Box(
                                modifier = Modifier
                                    .width(50.dp)
                                    .clip(RoundedCornerShape(10.dp))
                                    .background(Blue),
                                contentAlignment = Alignment.Center
                            ) {
                                Text("EUR")
                            }
                            Text("1.428.39")
                            Text("▼2.81(0.19%)", fontSize = 12.sp, color = Color.Blue)
                        }
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                    Text("환전 시, 매매기준 기반의 별도 환율 따름", fontSize = 12.sp, color = deepgray)
                }
            }
        }
        Box(
            modifier = Modifier
                .padding(top = 10.dp)
                .padding(start = 30.dp)
        ) {
            Column {
                Text("나의 계좌에서... 에서")
                Text("잔액 ${formattedBalance} ₩", fontSize = 15.sp, color = Gray)
            }
        }

        Box(
            modifier = Modifier
                .padding(top = 10.dp)
                .padding(start = 30.dp)
                .border(1.dp, Color.LightGray, RoundedCornerShape(4.dp)),
        ) {
            Row(
                modifier = Modifier.clickable(onClick = { expanded = true })
                    .padding(5.dp),
                verticalAlignment = Alignment.CenterVertically, // 세로 방향으로 가운데 정렬
                horizontalArrangement = Arrangement.Center // 가로 방향으로 가운데 정렬
            ) {
                Text(
                    text = selectedCurrency,
                )
                Spacer(Modifier.width(5.dp))
                Image(painter = arrowIcon, contentDescription = "arrow Icon")
            }

            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
            ) {
                items.forEach { label ->
                    DropdownMenuItem(onClick = {
                        // 항목을 클릭하면 할 일
                        selectedCurrency = label
                        expanded = false
                    }) {
                        Text(text = label)
                    }
                }
            }
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Box(
                modifier = Modifier
                    .width(320.dp)
                    .align(Alignment.Center)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    TextField(
                        modifier = Modifier.fillMaxHeight(),
                        value = money,
                        onValueChange = {
                            if (it.all { char -> char.isDigit() }) money = it
                        }, // 숫자만 허용
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number), // 숫자 키보드 사용
                        shape = RoundedCornerShape(16.dp),
                        textStyle = TextStyle(fontSize = 17.sp),
                        placeholder = { Text("환전할 액수를 입력해주세요", fontSize = 14.sp) },
                        colors = TextFieldDefaults.textFieldColors(
                            backgroundColor = Color.White,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent
                        )
                    )
                    Text(
                        text = selectedCurrency, fontSize = 13.sp
                    )
                }

                Spacer(
                    Modifier
                        .align(Alignment.BottomCenter)
                        .fillMaxWidth()
                        .height(1.dp)
                        .background(Color.Black)
                )
            }
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp)
        ) {
            Image(
                painter = bigarrowIcon,
                contentDescription = "bigarrow Icon",
                modifier = Modifier
                    .width(30.dp)
                    .height(30.dp)
                    .align(Alignment.Center)
            )
        }

        Row(
            modifier = Modifier
                .padding(start = 30.dp)
        ) {
            Text(
                text = unselectedItem,
                fontSize = 17.sp
            )
            Text("로", fontSize = 17.sp)
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Box(
                modifier = Modifier
                    .width(320.dp)
                    .align(Alignment.Center)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Text(
                        text = unselectedItem, fontSize = 13.sp
                    )

                    Spacer(Modifier.width(30.dp)) // Add some space between the items.
                    Text(
                        text = money, fontSize = 17.sp // Display the value of 'money'.
                    )
                }
                Spacer(
                    Modifier
                        .align(Alignment.BottomCenter)
                        .fillMaxWidth()
                        .height(1.dp)
                        .background(Color.Black)
                )
            }
        }


        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 15.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            Box(
                modifier = Modifier
                    .width(90.dp)
                    .height(36.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(DeepBlue)
                    .clickable {
                        coroutineScope.launch {
                            if (selectedCurrency == "달러") {
                                TradedAPI().moneyToPoint(id!!, money!!)
                            } else if (selectedCurrency == "포인트") {
                                TradedAPI().pointToMoney(id!!, money!!)
                            }
                            showConfirmationDialog = true
                        }
                    },
                contentAlignment = Alignment.Center
            ) {
                Text("환전하기", color = Color.White)
            }

            if (showConfirmationDialog) {
                AlertDialog(
                    onDismissRequest = { showConfirmationDialog = false },
                    title = { Text("환전 완료") },
                    text = { Text("환전이 성공적으로 완료되었습니다.", fontSize = 20.sp) },
                    confirmButton = {
                        Button(onClick= {
                            showConfirmationDialog= false
                            navigator.navigate("/home") // Navigate to the '/home' route.
                        }) {
                            Text("확인")
                        }
                    }
                )
            }
        }
    }
}

