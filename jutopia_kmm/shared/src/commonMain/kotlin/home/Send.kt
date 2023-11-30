package home

import BottomTabBar
import UserInfo
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.ui.semantics.clearAndSetSemantics
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
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import moe.tlaster.precompose.navigation.Navigator
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import pathTo

@kotlinx.serialization.Serializable
data class StudentData(
    val student_name: String,
    val student_number: Int
)

@Serializable
data class ClassResponse(
    val api_status: String,
    val message: String?,
    val data: List<StudentData>
)
class Send {
    private val client = HttpClient(CIO) {
        install(ContentNegotiation) {
            json(
                Json { ignoreUnknownKeys = true }
            )
        }
    }

    suspend fun getClass(classId: String): ClassResponse {
        val response: HttpResponse = client.get("http://j9c108.p.ssafy.io:8000/member-server/api/account/classroom/studentlist?classroomId=$classId")
        val body: String = response.bodyAsText()

        // Parse the JSON response.
        val json = Json { ignoreUnknownKeys = true }

        return json.decodeFromString<ClassResponse>(body)
    }

}

private val log = Logger.withTag("Send")
@OptIn(ExperimentalResourceApi::class)
@Composable
fun Send(navigator: Navigator) {

    var name by remember { mutableStateOf("") }

    var glassesImg = "drawable/glasses.xml"
    var humanImg = "drawable/human.xml"

    val glassesIcon: Painter = painterResource(glassesImg)
    val humanIcon: Painter = painterResource(humanImg)

    var selectedTab by remember { mutableStateOf(0) }
    var classId by remember { mutableStateOf("") }
    val store: KStore<UserInfo> = storeOf(filePath = pathTo("user"))
    var students by remember { mutableStateOf(emptyList<StudentData>()) }

    LaunchedEffect(1) {
        val temp: UserInfo? = store.get()
        if (temp != null) {
            classId = temp.classUUID
            val send = Send()
            val classStudent = send.getClass(classId)
            students = classStudent.data  // Update the 'students' state.
            log.i { "반 학생들= $students" }
        }
    }

    Column(
        verticalArrangement= Arrangement.spacedBy(15.dp)
    ) {
        TopPageBar("송금하기", navigator=navigator)
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(55.dp)
                .align(Alignment.CenterHorizontally),
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ){
                TextField(
                    modifier = Modifier
                        .width(300.dp),
                    value = name,
                    onValueChange = { name = it }, // 모든 글자 허용
                    placeholder = { if (name.isEmpty()) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Image(painter = glassesIcon, contentDescription = "glasses Icon")
                            Spacer(Modifier.width(10.dp))
                            Text("이름 검색", fontSize = 15.sp, color = Color.White)
                            }
                        }
                      },
                    colors = TextFieldDefaults.textFieldColors(
                        textColor = Color.White,
                        backgroundColor = deepBlue,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    )
                )

            }
        }
        students.forEachIndexed { index, student ->
            Box(
                modifier = Modifier
                    .width(300.dp)
                    .height(35.dp)
                    .clickable {
                        navigator.navigate("/send_detail/${student.student_name}/${student.student_number}")
                    }
                    .align(Alignment.CenterHorizontally),
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Image(painter = humanIcon, contentDescription = "human Icon")
                    Spacer(modifier = Modifier.width(10.dp))
                    Text("이름: ${student.student_name} / 번호: ${student.student_number}")
                }
            }
        }
//

    }

    BottomTabBar(navigator, 0)
}