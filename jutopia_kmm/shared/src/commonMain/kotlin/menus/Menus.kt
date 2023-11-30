package menus

import BottomTabBar
import UserInfo
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.KeyboardArrowRight
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import co.touchlab.kermit.Logger
import icehimchanFontFamily
import icesiminFontFamily
import io.github.xxfast.kstore.KStore
import io.github.xxfast.kstore.file.storeOf
import kotlinx.coroutines.launch
import moe.tlaster.precompose.navigation.Navigator
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import pathTo

@Composable
fun Menus(navigator: Navigator) {

    val coroutineScope = rememberCoroutineScope()

    val store: KStore<UserInfo> = storeOf(filePath = pathTo("user"))

    var userInfo by remember { mutableStateOf<UserInfo?>(null) }

    coroutineScope.launch {
        userInfo = store.get()
    }

    if (userInfo != null)
    {
        Column {
            Contents(userInfo!!, navigator)
        }
    }
    BottomTabBar(navigator, 4)
}

@OptIn(ExperimentalResourceApi::class)
@Composable
fun Contents(userInfo: UserInfo, navigator: Navigator) {

    val coroutineScope = rememberCoroutineScope()

    val store: KStore<UserInfo> = storeOf(filePath = pathTo("user"))

    fun logout() {
        coroutineScope.launch {
            store.delete()
            navigator.navigate("/mainpage")
        }
    }

    Column {
        UserBoard(userInfo)
        MenuHeader("내 정보")
        MenuItem("drawable/menu_human.xml", "비밀번호 변경") { navigator.navigate("/changepassword") }
        MenuItem("drawable/menu_door.xml", "로그아웃") { logout() }
    }
}

@Composable
fun UserBoard(userInfo: UserInfo) {
    Box (
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxWidth()
            .background(color = Color(0xFF985108))
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .padding(12.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.spacedBy(12.dp,Alignment.Top),
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = Color(0xFF358438))
                    .padding(start = 24.dp, end = 24.dp, top = 12.dp, bottom = 12.dp)
            ) {
                Text(
                    userInfo.school,
                    fontSize = 24.sp,
                    color = Color.White,
                    fontFamily = icesiminFontFamily
                )
                Row (
                    horizontalArrangement = Arrangement.End,
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Column(
                        horizontalAlignment = Alignment.Start,
                    ) {
                        Text(
                            "이름 : ${userInfo.name}",
                            fontSize = 24.sp,
                            color = Color.White,
                            fontFamily = icehimchanFontFamily
                        )
                        Text(
                            "번호 : ${userInfo.studentNumber}번",
                            fontSize = 24.sp,
                            color = Color.White,
                            fontFamily = icehimchanFontFamily
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun MenuHeader(text: String) {
    Box(
        modifier = Modifier
            .padding(12.dp)
    ) {
        Text(text, fontSize = 16.sp, color = Color.LightGray)
    }
    Divider(startIndent = 20.dp)
}

@OptIn(ExperimentalResourceApi::class)
@Composable
fun MenuItem(svgPath: String, text: String, onMenuClick: () -> Unit) {
    Column (
        modifier = Modifier
            .clickable { onMenuClick() }
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .padding(top = 20.dp, bottom = 20.dp, start = 12.dp, end = 12.dp)
                .fillMaxWidth()
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Image(painterResource(svgPath), "", modifier = Modifier.width(60.dp))
                Text(text)
            }
            Icon(Icons.Default.KeyboardArrowRight, "")
        }
        Divider(startIndent = 80.dp)
    }
}