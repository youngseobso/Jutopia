package news

import BottomTabBar
import Variables.ColorsOnPrimary
import Variables.ColorsPrimary
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import co.touchlab.kermit.Logger
import com.svenjacobs.reveal.Reveal
import com.svenjacobs.reveal.RevealCanvasState
import com.svenjacobs.reveal.rememberRevealState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import moe.tlaster.precompose.navigation.Navigator
import moe.tlaster.precompose.viewmodel.viewModel
import openUrl
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource

enum class Keys { ModalKey }

@Composable
fun News(navigator: Navigator, revealCanvasState: RevealCanvasState) {

    val revealState = rememberRevealState()
    val coroutineScope = rememberCoroutineScope()

    Reveal(
        revealCanvasState = revealCanvasState,
        revealState = revealState,
        onRevealableClick = {},
        onOverlayClick = {},
    ) {
        Contents()
        BottomTabBar(navigator, 3)
    }
}

@OptIn(ExperimentalResourceApi::class)
@Composable
fun Contents() {

    val categories: List<NewsCategory> = (
                listOf(
                    NewsCategory(0, "삼성", "samsung.png"),
                    NewsCategory(1, "현대차", "hyundai.png"),
                    NewsCategory(2, "한화", "hanhwa.png"),
                    NewsCategory(3, "네이버", "naver.png"),
                    NewsCategory(4, "SM엔터", "sm.png"),
                    ))

    val stockNames = listOf("삼성전자", "현대차", "한화", "네이버", "에스엠")

    var idx by remember {mutableStateOf(0)}

    Column {
        TabRow(
            selectedTabIndex = idx,
            backgroundColor = ColorsPrimary,
            contentColor = ColorsOnPrimary,
            modifier = Modifier
                .height(80.dp)
        ) {
            categories.map { category ->

                var isSelected: Boolean = idx == category.idx

                Tab(
                    selected = isSelected,
                    onClick = { idx = category.idx }

                ) {
                    Image(
                        painterResource("drawable/logo_" + category.imgSrc),
                        "",
                        alpha = if(isSelected) 1f else 0.4f,
                        modifier = Modifier.height(32.dp).width(40.dp)
                    )
                    Text(category.brand, fontSize = 12.sp, color = if(isSelected) Color(0xFFFFFFFF) else Color(0x66FFFFFF))
                }
            }
        }
        var searchStr by remember {mutableStateOf("")}

        Row (
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp)
                .background(color = ColorsPrimary)
                .padding(12.dp)
        ) {
            TextField(
                value = searchStr,
                onValueChange = { searchStr = it },
                trailingIcon = { Icon(Icons.Default.Search, contentDescription = "", tint = Color(0x66FFFFFF)) },
                colors = TextFieldDefaults.textFieldColors(
                    textColor = Color(0xFF000000),
                    backgroundColor = Color(0x66212121),
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    trailingIconColor = ColorsOnPrimary
                ),
                shape = RoundedCornerShape(12.dp),
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
            )
        }

        val newsListViewModel: NewsViewModel = viewModel(NewsViewModel::class, keys = listOf(idx.toString() + searchStr)) {
            NewsViewModel()
        }

        NewsList(newsListViewModel, searchStr, stockNames[idx])
    }
}

@Composable
fun NewsList(viewModel: NewsViewModel, searchStr: String, stockName: String) {

    var isInitial = remember { mutableStateOf(true) }

    val log = Logger.withTag("long")

    val coroutineScope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    val openDialog = remember { mutableStateOf(false) }
    val dialogTitle = remember { mutableStateOf("") }
    val dialogText = remember { mutableStateOf("") }

    when {
        openDialog.value -> {
            AlertDialog(title = {Text(dialogTitle.value)}, text = { Text(dialogText.value) }, buttons = {
                Row (
                    horizontalArrangement = Arrangement.End,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                ) {
                    Button(onClick = { openDialog.value = false }) { Text("확인") }
                } }, onDismissRequest = {openDialog.value = false}, backgroundColor = Color.LightGray, modifier = Modifier.shadow(4.dp))
        }
    }

    Scaffold(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 92.dp),
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        }
    ) {
        coroutineScope.launch {
            if (isInitial.value) {
                snackbarHostState.showSnackbar("궁금한 뉴스를 길게 누르면 설명을 볼 수 있어요~")
                isInitial.value = false
            }
        }
        val newses by viewModel.newses.collectAsState()
        val isLoading by viewModel.isLoading.collectAsState()
        if (isLoading) viewModel.fetchData(stockName)

        LazyColumn (
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier
                .padding(8.dp)

        ) {
            items(newses.filter { newsDetail -> newsDetail.title.contains(searchStr) }) {newsItem ->

                val isLoading = remember { mutableStateOf(false) }
                if(isLoading.value) {
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
                    Column (
                        modifier = Modifier
                            .pointerInput(Unit) {
                                detectTapGestures (
                                    onTap = { openUrl(newsItem.link) },
                                    onLongPress = {
                                        coroutineScope.launch {
                                            isLoading.value = true
                                            var response = NewsAPI().getSummary(newsItem.link)
                                            openDialog.value = true
                                            dialogTitle.value = newsItem.title
                                            dialogText.value = response
                                            isLoading.value = false
                                        }
                                                  },
                                )
                            }
                    ) {
                        Text(
                            newsItem.title,
                            fontSize = 20.sp,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(
                                horizontalArrangement = Arrangement.spacedBy(20.dp),
                            ) {
                                Text(newsItem.date, color = Color(0xFF9E9E9E))
                                Text(newsItem.time, color = Color(0xFF9E9E9E))
                            }
                            Text("|", color = Color(0xFF9E9E9E))
                            Text(newsItem.publisher, color = Color(0xFF9E9E9E))
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        Divider(color = Color(0x22000000))
                    }
                }
            }
        }
    }
}