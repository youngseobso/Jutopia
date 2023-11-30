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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
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
import co.touchlab.kermit.Logger
import com.svenjacobs.reveal.Reveal
import com.svenjacobs.reveal.RevealCanvasState
import com.svenjacobs.reveal.rememberRevealState
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
import io.ktor.content.TextContent
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
import kotlin.js.JsName

@Serializable
data class Product(
    val id: String,
    val productName: String,
    val productDetail: String,
    val interestRate: Double,
    val maxMoney: Double,
    val minMoney: Double,
    val term: Int
)
@Serializable
data class ServerResponse(
    val result: Result,
    val body: List<Product>
)

@kotlinx.serialization.Serializable
data class Result(
    val resultCode: Int,
    val resultMessage: String,
    val resultDescription: String
)

private val log = Logger.withTag("Save")

class saveAPI {
    private val client = HttpClient(CIO) {
        install(ContentNegotiation){
            json(
                Json { ignoreUnknownKeys = true }
            )
        }
    }

    suspend fun getSave(classroomId: String): List<Product> {
        log.i { "$classroomId" }
        val response: HttpResponse = client.get("http://j9c108.p.ssafy.io:8000/class-server/api/bank/$classroomId/product")
        val body: String = response.bodyAsText()
        // JSON 문자열을 객체로 변환
        val result = Json.decodeFromString<ServerResponse>(body)
        // result.body 에 있는 List<Product> 반환
        log.i { "$response c" }
        return result.body
    }

    @OptIn(InternalAPI::class)
    suspend fun subscriptionSave(memberId: String, productId: String?, money: String) {
        val money = money.toDouble()
        val requestBody = mapOf("money" to money)
        val response: HttpResponse = client.post("http://j9c108.p.ssafy.io:8000/class-server/api/bank/product/$memberId/$productId") {
            contentType(ContentType.Application.Json)
            body = Json.encodeToString(requestBody)
        }

        log.i { "$response" }
    }
}

val skyBlue = Color(0xFFEBF5F7)
val deepSky = Color(0xFFC3E0E8)
val deepNavy = Color(0xFF316781)
enum class Keys { subscript }
@OptIn(ExperimentalResourceApi::class)
@Composable
fun Save(navigator: Navigator, revealCanvasState: RevealCanvasState) {
    var firstProduct by remember { mutableStateOf<Product?>(null) }
    val coroutineScope = rememberCoroutineScope()
    var student_member_id by remember { mutableStateOf("") }
    var classroomId by remember { mutableStateOf("") }


    val store: KStore<UserInfo> = storeOf(filePath = pathTo("user"))
    LaunchedEffect(1) {
        val temp: UserInfo? = store.get()
        if (temp != null) {
            student_member_id = temp.uuid
            classroomId = temp.classUUID
            var test = saveAPI()
            var products = test.getSave(classroomId)
            log.i {"$products"}
            firstProduct = products[0]
        }
    }



    var productId  = firstProduct?.id
    var minMoney = firstProduct?.minMoney?.toInt()
    var maxMoney = firstProduct?.maxMoney?.toInt()
    var productName = firstProduct?.productName
    var productDetail = firstProduct?.productDetail
    var interestRate = firstProduct?.interestRate
    var term = firstProduct?.term
    var showConfirmationDialog by remember { mutableStateOf(false) }

    fun formatThousandSeparator(number: Int?): String? {
        return number?.let {
            it.toString().reversed().chunked(3).joinToString(",").reversed()
        }
    }

    val formattedMinMoney = formatThousandSeparator(minMoney)
    val formattedMaxMoney = formatThousandSeparator(maxMoney)

    log.i { "productId = $productId, minMoney = $minMoney, maxMoney = $maxMoney, productName = $productName, productDetail = $productDetail, interestRate = $interestRate, term = $term"}

    val revealState = rememberRevealState()
    val scope = rememberCoroutineScope()
    var moneyImg = "drawable/money.xml"
    var checkImg = "drawable/check.xml"
    var heartImg = "drawable/heart.xml"
    var rabbit_coinImg = "drawable/rabbit_coin.xml"

    val moneyIcon: Painter = painterResource(moneyImg)
    val checkIcon: Painter = painterResource(checkImg)
    val heartIcon: Painter = painterResource(heartImg)
    val rabbit_coinIcon: Painter = painterResource(rabbit_coinImg)

    var showDialog by remember { mutableStateOf(false) }
    var money by remember { mutableStateOf("") }
    var showLimitWarningDialog by remember { mutableStateOf(false) }

    Reveal(
        revealCanvasState = revealCanvasState,
        revealState = revealState,
        onRevealableClick = {},
        onOverlayClick = {
                         scope.launch {
                             revealState.hide()
                         }
        },
    ) {

        Column {
            TopPageBar("적금정보", navigator = navigator)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(280.dp)
                    .background(skyBlue)
            ) {
                Column(
                    modifier = Modifier
                        .padding(top = 15.dp)
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("6학년 1반", fontSize = 25.sp)
                    Row {
                        Text("$productName ", fontSize = 25.sp, color = deepNavy)
                        Text("적금", fontSize = 25.sp)
                    }
                    Row(
                        modifier = Modifier
                            .padding(top = 15.dp)
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Image(painter = moneyIcon, contentDescription = "Money Icon")
                            Text("최고이자율", fontSize = 10.sp)
                            Text("월 $interestRate%", fontSize = 15.sp)
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text("($term", fontSize = 10.sp)
                                Text("개월)", fontSize = 10.sp)
                            }
                        }
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Image(painter = checkIcon, contentDescription = "check Icon")
                            Text("저축한도", fontSize = 10.sp)
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text("$formattedMinMoney", fontSize = 15.sp)
                                Text("원 이상", fontSize = 15.sp)
                            }
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text("$formattedMaxMoney", fontSize = 15.sp)
                                Text("원 이하", fontSize = 15.sp)
                            }
                        }
                    }
                    Row(
                        modifier = Modifier
                            .padding(top = 15.dp)
                    ) {

                        Box(
                            modifier = Modifier
                                .revealable( key = Keys.subscript)
                                .width(180.dp)
                                .height(30.dp)
                                .background(deepSky)
                                .clickable {
                                    showDialog = true
                                    scope.launch {
                                        revealState.reveal(Keys.subscript)
                                    }
                                           },
                            contentAlignment = Alignment.Center
                        ) {
                            Text("가입하기", color = deepNavy)
                        }

                        if (showDialog) {
                            AlertDialog(
                                modifier = Modifier,
                                backgroundColor = skyBlue,
                                onDismissRequest = { showDialog = false },
                                text = {
                                    TextField(
                                        value = money,
                                        onValueChange = { money = it },
                                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                        placeholder = { Text("금액을 입력해주세요", fontSize = 15.sp) },
                                        colors = TextFieldDefaults.textFieldColors(
                                            backgroundColor = Color.White,
                                            focusedIndicatorColor = Color.Transparent,
                                            unfocusedIndicatorColor = Color.Transparent
                                        )
                                    )
                                    Spacer(
                                        Modifier
                                            .fillMaxWidth()
                                            .height(1.dp)
                                            .background(Color.Gray)
                                    )
                                },

                                dismissButton= {
                                    Button(onClick = {
                                        showDialog = false
                                        scope.launch {
                                            revealState.hide()
                                        }
                                    },
                                        colors = ButtonDefaults.buttonColors(deepSky)
                                    ) {
                                        Text("취소")
                                    }
                                },

                                confirmButton = {
                                    Button(onClick = {
                                        showDialog = false
                                        scope.launch {
                                            revealState.hide()
                                        }

                                        // money 값이 minMoney와 maxMoney 사이에 있는지 확인
                                        val min = minMoney ?: 0
                                        val max = maxMoney ?: Int.MAX_VALUE
                                        val moneyValue = money.toDoubleOrNull()

                                        if (moneyValue != null && moneyValue in min.toDouble()..max.toDouble()) {
                                            val api = saveAPI()
                                            coroutineScope.launch {
                                                api.subscriptionSave(student_member_id, productId, money)
                                                showConfirmationDialog = true  // Show the confirmation dialog after subscription is done.
                                            }

                                        } else {
                                            // 저축 한도를 확인해주세요 다이얼로그 띄우기
                                            showLimitWarningDialog = true
                                        }
                                    },
                                        colors = ButtonDefaults.buttonColors(deepSky)
                                    ) {
                                        Text("확인")
                                    }
                                }
                            )
                        }
                        if (showConfirmationDialog) {
                            AlertDialog(
                                onDismissRequest = { showConfirmationDialog = false },
                                title= {"확인"},
                                text={
                                    Text("적금 가입이 완료되었습니다.", style=TextStyle(color=Color.Black, fontSize=20.sp))
                                },
                                confirmButton={
                                    Button(onClick={showConfirmationDialog=false}){Text("확인")}
                                }
                            )
                        }

                        if (showLimitWarningDialog) {  // if the state is true, show the dialog
                            AlertDialog(
                                onDismissRequest = { showLimitWarningDialog = false },  // when dismissed, set the state to hide the dialog
                                title= {"경고"},
                                text={
                                    Text("저축한도를 확인해주세요.", style = TextStyle(color = Color.Black, fontSize = 20.sp))
                                },
                                confirmButton={
                                    Button(onClick={showLimitWarningDialog=false}){Text("확인")}
                                }
                            )
                        }

                        Spacer(modifier = Modifier.width(10.dp))

                        Box(
                            modifier = Modifier
                                .width(30.dp)
                                .height(30.dp)
                                .background(Color.White),
                            contentAlignment = Alignment.Center
                        ) {
                            Image(painter = heartIcon, contentDescription = "heart Icon")
                        }
                    }
                }
            }

            Box(
                modifier = Modifier
                    .padding(top = 15.dp)
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Row {
                    Column {
                        Text("아름다운 저축을 위한 습관,", color = Color.Black)
                        Row {
                            Text("주토피아", color = deepNavy)
                            Text("와 함께 실천해요", color = Color.Black)
                        }
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    Image(
                        painter = rabbit_coinIcon, contentDescription = "check Icon",
                        modifier = Modifier.width(100.dp).height(100.dp)
                    )
                }
            }
        }
    }
    BottomTabBar(navigator, 0)
}