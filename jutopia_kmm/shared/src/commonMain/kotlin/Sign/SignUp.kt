package Sign

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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.Checkbox
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import co.touchlab.kermit.Logger
import common.TopPageBar
import home.deepSky
import home.startColor
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.launch
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import moe.tlaster.precompose.navigation.Navigator
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource

private val log = Logger.withTag("SignUp")
val deepsky = Color(0xFF8FE0FF)

@Serializable
data class ResultData(
    @SerialName("result_code")
    val resultCode: Int,
    @SerialName("result_message")
    val resultMessage: String,
    @SerialName("result_description")
    val resultDescription: String
)

@Serializable
data class DuplicatedResponse(
    val result: ResultData,
    val body: Boolean
)

class SignUpAPI {
    private val client = HttpClient(CIO) {
        install(ContentNegotiation) {
            json(
                Json { ignoreUnknownKeys = true }
            )
        }
    }

    suspend fun duplicated(studentId: String): DuplicatedResponse {
        val response: HttpResponse =
            client.get("http://j9c108.p.ssafy.io:8000/member-server/api/student/sign-up/$studentId/duplicated")
        return Json.decodeFromString<DuplicatedResponse>(response.bodyAsText())
    }
}

@OptIn(ExperimentalResourceApi::class, ExperimentalComposeUiApi::class)
@Composable
fun SignUp(navigator: Navigator) {
    var ID by remember { mutableStateOf("") }
    var PassWord by remember { mutableStateOf("") }
    var checkPassword by remember { mutableStateOf("") }
    var selectedTab by remember { mutableStateOf(0) }
    val coroutineScope = rememberCoroutineScope()
    var duplicationCheck by remember { mutableStateOf(true) }
    var showAlertForDuplication by remember { mutableStateOf(false) }
    var alertMessageForDuplication by remember { mutableStateOf("") }
    var showAlertForDuplicationCheck by remember { mutableStateOf(false) }
    var alertMessageForDuplicationCheck by remember { mutableStateOf("") }
    var showAlertForInput by remember { mutableStateOf(false) }
    var alertMessageForInput by remember { mutableStateOf("") }
    var showAlertForRole by remember { mutableStateOf(false) }
    var alertMessageForRole by remember { mutableStateOf("") }
    var isStudent by remember { mutableStateOf(false) }
    var isTeacher by remember { mutableStateOf(false) }
    var schoolImg = "drawable/school.png"
    val schoolIcon: Painter = painterResource(schoolImg)

    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current

    Column {
        Column(
            modifier = Modifier,
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TopPageBar("회원가입", navigator = navigator, showChatBot = false, bgColor = startColor)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(130.dp)
                    .background(sky)
            ) {
                Image(
                    painter = schoolIcon, contentDescription = "School Icon",
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(end = 30.dp)
                        .size(70.dp)
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    modifier = Modifier
                        .width(140.dp)
                        .height(80.dp)
                ) {
                    Text("ID")
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight()
                            .clip(RoundedCornerShape(5.dp))
                            .border(2.dp, Color.LightGray)
                    ) {
                        TextField(
                            modifier = Modifier
                                .width(300.dp),
                            value = ID,
                            onValueChange = { ID = it }, // 모든 글자 허용
                            placeholder = { if (ID.isEmpty()) Text("Enter your ID") },
                            textStyle = TextStyle(fontSize = 20.sp),
                            colors = TextFieldDefaults.textFieldColors(
                                textColor = Color.Black,
                                backgroundColor = Color.White,
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent
                            ),
                            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
                            keyboardActions = KeyboardActions(onDone = {
                                keyboardController?.hide()
                                focusManager.clearFocus()
                            })
                        )
                    }
                }
                Spacer(modifier = Modifier.width(10.dp))
                Box(
                    modifier = Modifier
                        .width(80.dp)
                        .height(60.dp)
                        .padding(top = 20.dp)
                        .clip(RoundedCornerShape(5.dp))
                        .border(2.dp, Color.LightGray)
                        .clickable {
                            if (ID.isEmpty()) {
                                alertMessageForDuplication = "ID를 입력해주세요."
                                showAlertForDuplication = true
                            } else {
                                coroutineScope.launch {
                                    var duplication = SignUpAPI()
                                    var result = duplication.duplicated(ID)
                                    var bodyValue = result.body
                                    log.i { "result == $result" }
                                    if (!bodyValue) {
                                        duplicationCheck = false
                                        alertMessageForDuplication = "사용 가능한 ID입니다."
                                    } else {
                                        alertMessageForDuplication = "사용할 수 없는 ID입니다."
                                    }
                                    showAlertForDuplication = true
                                }
                            }
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Text("중복확인")
                }
                if (showAlertForDuplication) {
                    AlertDialog(
                        onDismissRequest = { showAlertForDuplication = false },
                        title = { Text(text = "알림") },
                        text = { Text(text = alertMessageForDuplication, fontSize = 20.sp) },
                        confirmButton = {
                            Button(onClick = { showAlertForDuplication = false }) {
                                Text("확인")
                            }
                        })
                }
            }

            Spacer(modifier = Modifier.height(5.dp))

            Column(
                modifier = Modifier
                    .width(220.dp)
                    .height(80.dp)
            ) {
                Text("비밀번호")
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                        .clip(RoundedCornerShape(5.dp))
                        .border(2.dp, Color.LightGray)
                ) {
                    TextField(
                        modifier = Modifier
                            .width(300.dp),
                        value = PassWord,
                        onValueChange = { PassWord = it }, // 모든 글자 허용
                        placeholder = { if (PassWord.isEmpty()) Text("Enter your PassWord") },
                        textStyle = TextStyle(fontSize = 20.sp),
                        visualTransformation = PasswordVisualTransformation(), // 비밀번호 가리기
                        colors = TextFieldDefaults.textFieldColors(
                            textColor = Color.Black,
                            backgroundColor = Color.White,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent
                        ),
                        keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
                        keyboardActions = KeyboardActions(onDone = {
                            keyboardController?.hide()
                            focusManager.clearFocus()
                        })
                    )
                }
            }

            Spacer(modifier = Modifier.height(5.dp))

            Column(
                modifier = Modifier
                    .width(220.dp)
                    .height(80.dp)
            ) {
                Text("비밀번호 확인")
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                        .clip(RoundedCornerShape(5.dp))
                        .border(2.dp, Color.LightGray)
                ) {
                    TextField(
                        modifier = Modifier
                            .width(300.dp),
                        value = checkPassword,
                        onValueChange = { checkPassword = it }, // 모든 글자 허용
                        placeholder = { if (checkPassword.isEmpty()) Text("Enter your PassWord") },
                        textStyle = TextStyle(fontSize = 20.sp),
                        visualTransformation = PasswordVisualTransformation(), // 비밀번호 가리기
                        colors = TextFieldDefaults.textFieldColors(
                            textColor = Color.Black,
                            backgroundColor = Color.White,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent
                        ),
                        keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
                        keyboardActions = KeyboardActions(onDone = {
                            keyboardController?.hide()
                            focusManager.clearFocus()
                        })
                    )
                }
            }

            Spacer(modifier = Modifier.height(5.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = isStudent,
                    onCheckedChange = {
                        isStudent = it
                        if (it) {
                            // 학생이 선택되면 선생님을 비선택 상태로
                            isTeacher = false
                        }
                    },
                )
                Text("학생")

                Checkbox(
                    checked = isTeacher,
                    onCheckedChange = {
                        isTeacher = it
                        if (it) {
                            // 선생님이 선택되면 학생을 비선택 상태로
                            isStudent = false
                        }
                    },
                )
                Text("선생님")
            }


            Spacer(modifier = Modifier.width(10.dp))


            Box(
                modifier = Modifier
                    .width(70.dp)
                    .height(38.dp)
                    .clip(RoundedCornerShape(5.dp))
                    .border(2.dp, Color.LightGray)
                    .clickable {
                        if (duplicationCheck) {
                            alertMessageForDuplicationCheck = "중복확인을 해주세요."
                            showAlertForDuplicationCheck = true
                        } else if (ID.isEmpty() || PassWord.isEmpty()) {
                            alertMessageForInput = "아이디와 비밀번호를 입력해주세요."
                            showAlertForInput = true
                        } else if (!isStudent && !isTeacher) {
                            alertMessageForRole = "학생 또는 선생님을 체크해주세요."
                            showAlertForRole = true
                        } else {
                            selectedTab = if (isStudent) 1 else if (isTeacher) 2 else selectedTab
                        }
                    },
                contentAlignment = Alignment.Center
            )
            {
                Text("확인", color = navy)
            }

            if (showAlertForDuplicationCheck) {
                AlertDialog(
                    onDismissRequest = { showAlertForDuplicationCheck = false },
                    title = { Text(text = "알림") },
                    text = { Text(text = alertMessageForDuplicationCheck, fontSize = 20.sp) },
                    confirmButton = {
                        Button(onClick = { showAlertForDuplicationCheck = false }) {
                            Text("확인")
                        }
                    })
            }

            if (showAlertForInput) {
                AlertDialog(
                    onDismissRequest = { showAlertForInput = false },
                    title = { Text(text = "알림") },
                    text = { Text(text = alertMessageForInput, fontSize = 17.sp) },
                    confirmButton = {
                        Button(onClick = { showAlertForInput = false }) {
                            Text("확인")
                        }
                    })
            }

            if (showAlertForRole) {
                AlertDialog(
                    onDismissRequest = { showAlertForRole = false },
                    title = { Text(text = "알림") },
                    text = { Text(text = alertMessageForRole, fontSize = 17.sp) },
                    confirmButton = {
                        Button(onClick = { showAlertForRole = false }) {
                            Text("확인")
                        }
                    })
            }


            when (selectedTab) {
                1 -> {
                    navigator.navigate("/studentsignup/${ID}/${PassWord}")
                }

                2 -> {
                    navigator.navigate("/teachersignup/${ID}/${PassWord}")
                }
            }
        }
    }
}