package Sign

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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
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
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import co.touchlab.kermit.Logger
import common.TopPageBar
import home.deepSky
import home.startColor
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.post
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import io.ktor.util.InternalAPI
import kotlinx.coroutines.launch
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import moe.tlaster.precompose.navigation.Navigator

private val log = Logger.withTag("TeacherSignUp")

class teacherSignUp {
    private val client = HttpClient(CIO) {
        install(ContentNegotiation) {
            json(
                Json { ignoreUnknownKeys = true }
            )
        }
    }

    @OptIn(InternalAPI::class)
    suspend fun signUpTeacher(
        teacher_id: String,
        teacher_pwd: String,
        teacher_name: String,
        school: String,
        grade: String,
        class_room: String
    ) {

        log.i { "$teacher_id, $teacher_pwd, $teacher_name, $school, $grade, $class_room" }

        val requestBody = mapOf(
            "student_id" to teacher_id,
            "student_pwd" to teacher_pwd,
            "student_name" to teacher_name,
            "school" to school,
            "grade" to grade,
            "class_room" to class_room,
        )

        try {
            val response: HttpResponse =
                client.post("http://j9c108.p.ssafy.io:8000/member-server/api/teacher/sign-up") {
                    contentType(ContentType.Application.Json)
                    body = Json.encodeToString(requestBody)
                }
            log.i { "$response" }
        } catch (e: Exception) {
            log.e(e) { "Error during sign up" }
        }
    }

    @OptIn(InternalAPI::class)
    suspend fun sendEmail(
        email_id: String,
    ) {

        val requestBody = mapOf(
            "email_id" to email_id,
        )

        try {
            val response: HttpResponse =
                client.post("http://j9c108.p.ssafy.io:8000/member-server/api/teacher/sign-in/mailConfirm") {
                    contentType(ContentType.Application.Json)
                    body = Json.encodeToString(requestBody)
                }
            log.i { "$response" }
        } catch (e: Exception) {
            log.e(e) { "메일 발송" }
        }
    }

    @OptIn(InternalAPI::class)
    suspend fun confirmCode(
        code: String,
    ): String { // Return type changed to String

        val requestBody = mapOf(
            "code" to code,
        )

        return try {
            val response: HttpResponse =
                client.post("http://j9c108.p.ssafy.io:8000/member-server/api/teacher/sign-in/verifyCode") {
                    contentType(ContentType.Application.Json)
                    body = Json.encodeToString(requestBody)
                }
            log.i { "인증코드: $response" }

            response.bodyAsText()
        } catch (e: Exception) {
            log.e(e) { "인증" }
            ""
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun TeacherSignUp(navigator: Navigator, teacher_id: String?, teacher_pwd: String?) {
    val coroutineScope = rememberCoroutineScope()
    var school by remember { mutableStateOf("") }
    var grade by remember { mutableStateOf("") }
    var class_room by remember { mutableStateOf("") }
    var teacher_name by remember { mutableStateOf("") }
    var verification by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var confirmResult by remember { mutableStateOf("") }
    var showDialog by remember { mutableStateOf(false) } // 추가
    var selectedTab by remember { mutableStateOf(0) }
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current
    var showSendEmailDialog by remember { mutableStateOf(false) }
    var emailResultMessage by remember { mutableStateOf("") }


    Column {
        TopPageBar("선생님 회원가입", navigator = navigator, showChatBot = false, bgColor = startColor)
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(modifier = Modifier.height(10.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    modifier = Modifier
                        .width(140.dp)
                        .height(80.dp)
                ) {
                    Text("이메일")
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
                            value = email,
                            onValueChange = { email = it }, // 모든 글자 허용
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
                        .width(70.dp)
                        .height(60.dp)
                        .padding(top = 20.dp)
                        .clip(RoundedCornerShape(5.dp))
                        .border(2.dp, Color.LightGray)
                        .clickable {
//
                        val api = teacherSignUp()
                        coroutineScope.launch {
                            api.sendEmail(email!!)
                            emailResultMessage="메일을 전송하였습니다."
                            showSendEmailDialog=true
                        }
                    },
                    contentAlignment = Alignment.Center
                ) {
                    Text("발송")
                }
            }
            if (showSendEmailDialog) {
                AlertDialog(
                    onDismissRequest = { showSendEmailDialog = false },
                    title = { Text(text = "메일 발송 결과") },
                    text = {
                        Text(text = emailResultMessage, fontSize = 20.sp)
                    },
                    confirmButton ={ Button(onClick ={showSendEmailDialog=false}){Text("확인")}}
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
                    Text("인증번호")
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
                            value = verification,
                            onValueChange = { verification = it }, // 모든 글자 허용
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
                        .width(70.dp)
                        .height(60.dp)
                        .padding(top = 20.dp)
                        .clip(RoundedCornerShape(5.dp))
                        .border(2.dp, Color.LightGray)
                        .clickable {
                            val api = teacherSignUp()
                            coroutineScope.launch {
                                confirmResult = api.confirmCode(
                                    verification!!,
                                )
                                showDialog = true // 인증이 완료되면 대화 상자 표시 설정
                            }
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Text("확인")
                }
            }

            if (showDialog) { // 대화 상자 표시 여부에 따라 AlertDialog 보여주기/숨기기 결정
                AlertDialog(
                    onDismissRequest = { showDialog = false },
                    title = { Text(text = "인증 결과") },
                    text = {
                        if (confirmResult == "false") {
                            Text(text ="인증이 완료되었습니다.", fontSize = 20.sp)
                        } else {
                            Text(text ="인증에 실패하였습니다.", fontSize = 20.sp)
                        }
                    },
                    confirmButton ={ Button(onClick ={showDialog=false}){Text("확인")}}
                )
            }

            Spacer(modifier = Modifier.height(10.dp))
            Column(
                modifier = Modifier
                    .width(220.dp)
                    .height(80.dp)
            ) {
                Text("학교")
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
                        value = school,
                        onValueChange = { school = it }, // 모든 글자 허용
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

            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Column(
                    modifier = Modifier
                        .width(80.dp)
                        .height(80.dp)
                ) {
                    Text("학년")
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
                            value = grade,
                            onValueChange = {
                                if (it.all { char -> char.isDigit() }) grade = it
                            }, // 숫자만 허용
                            keyboardOptions = KeyboardOptions.Default.copy(
                                keyboardType = KeyboardType.Number,
                                imeAction = ImeAction.Done
                            ), // 숫자 키보드 사용
                            textStyle = TextStyle(fontSize = 20.sp),
                            colors = TextFieldDefaults.textFieldColors(
                                textColor = Color.Black,
                                backgroundColor = Color.White,
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent
                            ),
                            keyboardActions = KeyboardActions(onDone = {
                                keyboardController?.hide()
                                focusManager.clearFocus()
                            })
                        )
                    }
                }
                Spacer(modifier = Modifier.width(55.dp))
                Column(
                    modifier = Modifier
                        .width(80.dp)
                        .height(80.dp)
                ) {
                    Text("반")
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
                            value = class_room,
                            onValueChange = {
                                if (it.all { char -> char.isDigit() }) class_room = it
                            }, // 숫자만 허용
                            keyboardOptions = KeyboardOptions.Default.copy(
                                keyboardType = KeyboardType.Number,
                                imeAction = ImeAction.Done
                            ), // 숫자 키보드 사용
                            textStyle = TextStyle(fontSize = 20.sp),
                            colors = TextFieldDefaults.textFieldColors(
                                textColor = Color.Black,
                                backgroundColor = Color.White,
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent
                            ),
                            keyboardActions = KeyboardActions(onDone = {
                                keyboardController?.hide()
                                focusManager.clearFocus()
                            })
                        )
                    }
                }
            }


            Spacer(modifier = Modifier.height(5.dp))
            Column(
                modifier = Modifier
                    .width(220.dp)
                    .height(80.dp)
            ) {
                Text("이름")
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
                        value = teacher_name,
                        onValueChange = { teacher_name = it }, // 모든 글자 허용
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


            Spacer(modifier = Modifier.height(10.dp))
            Spacer(modifier = Modifier.width(10.dp))

            Box(
                modifier = Modifier
                    .width(70.dp)
                    .height(38.dp)
                    .clip(RoundedCornerShape(5.dp))
                    .border(2.dp, Color.LightGray)
                    .clickable {
                        selectedTab = 1
                        val api = teacherSignUp()
                        coroutineScope.launch {
                            api.signUpTeacher(
                                teacher_id!!,
                                teacher_pwd!!,
                                teacher_name!!,
                                school!!,
                                grade!!,
                                class_room!!
                            )
                        }
                    },
                contentAlignment = Alignment.Center
            )
            {
                Text("회원가입", color = navy)
            }
        }

        when (selectedTab) {
            1 -> {
                navigator.navigate("/mainpage")
            }

        }
    }
}