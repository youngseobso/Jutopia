package school

import BottomTabBar
import UserInfo
import Variables.ColorsPrimary
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
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
import common.TopPageBar
import io.github.xxfast.kstore.KStore
import io.github.xxfast.kstore.file.storeOf
import kotlinx.coroutines.launch
import moe.tlaster.precompose.navigation.Navigator
import moe.tlaster.precompose.viewmodel.viewModel
import pathTo

@Composable
fun School(navigator: Navigator, viewModel : SchoolViewModel = viewModel(modelClass = SchoolViewModel::class) {
    SchoolViewModel()
}) {
    val coroutineScope = rememberCoroutineScope()

    val store: KStore<UserInfo> = storeOf(filePath = pathTo("user"))

    var userInfo by remember { mutableStateOf<UserInfo?>(null) }

    coroutineScope.launch {
        userInfo = store.get()
    }

    if (userInfo != null) {

        val notice by viewModel.notice.collectAsState()
        val isLoading by viewModel.isLoading.collectAsState()
        if (isLoading) viewModel.fetchData(userInfo!!.school, userInfo!!.grade, userInfo!!.classroom)

        Column {
            TopPageBar("학교", navigator, showReturn = false)
            if(isLoading) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(80.dp)
                ) {
                    CircularProgressIndicator(
                        color = ColorsPrimary,
                        backgroundColor = Color.LightGray,
                        modifier = Modifier.width(64.dp)
                    )
                }
            } else {
                Notification(notice, navigator)
            }
        }
    }


    BottomTabBar(navigator, 2)
}

@Composable
fun Notification(notice: List<NotiDetail>, navigator: Navigator) {
    LazyColumn (
        modifier = Modifier
            .padding(20.dp)
    ) {
        items(notice) {noticeItem ->
            Column (
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(
                        top = 4.dp,
                        bottom = 4.dp
                    ).clickable { navigator.navigate("/notice/${noticeItem.idx}") }
            ) {
                Text(noticeItem.title, fontSize = 28.sp)
                Row (
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.Top,
                ) {
                    Text(noticeItem.date, color = Color(0xFF9E9E9E))
                    Spacer(modifier = Modifier.width(20.dp))
                    Text(noticeItem.time, color = Color(0xFF9E9E9E))
                }
                Divider(thickness = 2.dp , color = Color(0x22000000))
            }
        }
    }
}