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


private val log = Logger.withTag("StudentSignUp")

class StudentSignUpAPI {
    private val client = HttpClient(CIO) {
        install(ContentNegotiation) {
            json(
                Json { ignoreUnknownKeys = true }
            )
        }
    }

    @OptIn(InternalAPI::class)
    suspend fun signUpStudent(
        student_id: String,
        student_pwd: String,
        student_name: String,
        school: String,
        grade: String,
        class_room: String,
        student_number: String
    ) {

        log.i { "$student_id, $student_pwd, $student_name, $school, $grade, $class_room, $student_number" }

        val requestBody = mapOf(
            "student_id" to student_id,
            "student_pwd" to student_pwd,
            "student_name" to student_name,
            "school" to school,
            "grade" to grade,
            "class_room" to class_room,
            "student_number" to student_number,
        )

        try {
            val response: HttpResponse =
                client.post("http://j9c108.p.ssafy.io:8000/member-server/api/student/sign-up") {
                    contentType(ContentType.Application.Json)
                    body = Json.encodeToString(requestBody)
                }
            log.i { "$response" }
        } catch (e: Exception) {
            log.e(e) { "Error during sign up" }
        }
    }

    suspend fun getClass(school: String, grade: String, class_room: String): String? {
        val encodedSchool = school.encodeURLQueryComponent()
        val response: HttpResponse =
            client.get("http://j9c108.p.ssafy.io:8000/member-server/api/student/$encodedSchool/$grade/$class_room")
        val body: String = response.bodyAsText()
        log.i { "$response" }
        log.i { "$body" }

        // JSON을 파싱하여 필요한 값을 추출
        return try {
            val json = Json { ignoreUnknownKeys = true }
            val result = json.decodeFromString<ResultResponse>(body)
            result.body
        } catch (e: Exception) {
            log.e(e) { "Error parsing JSON" }
            null
        }
    }

    @kotlinx.serialization.Serializable
    data class ResultResponse(
        val result: Result,
        val body: String
    )

    @Serializable
    data class Result(
        val result_code: Int,
        val result_message: String,
        val result_description: String
    )
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun StudentSignUp(navigator: Navigator, student_id: String?, student_pwd: String?) {
    val coroutineScope = rememberCoroutineScope()
    var school by remember { mutableStateOf("") }
    var grade by remember { mutableStateOf("") }
    var class_room by remember { mutableStateOf("") }
    var student_name by remember { mutableStateOf("") }
    var student_number by remember { mutableStateOf("") }
    var selectedTab by remember { mutableStateOf(0) }
    var getClassMessage by remember { mutableStateOf<String?>(null) }
    var showSchool by remember { mutableStateOf(false) }
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current
    log.i { " $student_id, $student_pwd " }

    Column {
        TopPageBar("학생 회원가입", navigator = navigator, showChatBot = false, bgColor = startColor)
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
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
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically

            ) {
                Column(
                    modifier = Modifier
                        .width(60.dp)
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
                Spacer(modifier = Modifier.width(15.dp))
                Column(
                    modifier = Modifier
                        .width(60.dp)
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
                Spacer(modifier = Modifier.width(15.dp))
                Box(
                    modifier = Modifier
                        .width(60.dp)
                        .height(60.dp)
                        .padding(top = 20.dp)
                        .clip(RoundedCornerShape(5.dp))
                        .border(2.dp, Color.LightGray)
                        .clickable {
                            val api = StudentSignUpAPI()
                            coroutineScope.launch {
                                val getClassId = api.getClass(school!!, grade!!, class_room!!)
                                if (getClassId != null) {
                                    getClassMessage = "조회에 성공하였습니다."
                                } else {
                                    getClassMessage = "조회에 실패하였습니다."
                                }
                                showSchool = true
                            }
                        },
                    contentAlignment = Alignment.Center
                ) {

                    Text("조회")
                }
            }

            if (showSchool && getClassMessage != null) {
                AlertDialog(
                    onDismissRequest = {
                        showSchool = false
                    },
                    title = {},
                    text = {
                        Text(getClassMessage!!, fontSize = 20.sp)
                    },
                    confirmButton = {
                        Button(onClick = { showSchool = false }) {
                            Text("확인")
                        }
                    },
                )
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
                        value = student_name,
                        onValueChange = { student_name = it }, // 모든 글자 허용
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

            Column(
                modifier = Modifier
                    .width(220.dp)
                    .height(80.dp)
            ) {
                Text("번호")
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
                        value = student_number,
                        onValueChange = { student_number = it }, // 모든 글자 허용
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
                        val api = StudentSignUpAPI()
                        coroutineScope.launch {
                            api.signUpStudent(
                                student_id!!,
                                student_pwd!!,
                                student_name!!,
                                school!!,
                                grade!!,
                                class_room!!,
                                student_number!!
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