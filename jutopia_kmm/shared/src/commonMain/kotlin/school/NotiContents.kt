@file:OptIn(ExperimentalResourceApi::class)

package school

import BottomTabBar
import Variables.ColorsPrimary
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.AbsoluteCutCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import common.TopPageBar
import icejaramFontFamily
import icesiminFontFamily
import moe.tlaster.precompose.navigation.Navigator
import moe.tlaster.precompose.viewmodel.viewModel
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource

@Composable
fun NotiContents(navigator: Navigator, idx: Int) {
    Column {
        TopPageBar("공지사항 상세", navigator)

        val contentViewModel =
            viewModel(modelClass = NotiContentsViewModel::class, keys = listOf(idx)) {
                NotiContentsViewModel(idx)
            }

        Contents(contentViewModel)
    }
    BottomTabBar(navigator, 2)
}

@Composable
fun Contents(viewModel: NotiContentsViewModel) {


    val notice by viewModel.notice.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    if (isLoading) viewModel.fetchDetailData()

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
        notice?.let { NoticeSticker(it) }
    }
}

@Composable
fun NoticeSticker(notice: NoticeDetail) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(
                top = 12.dp,
                start = 12.dp,
                bottom = 80.dp,
                end = 12.dp
            )
            .shadow(
                8.dp,
                shape = AbsoluteCutCornerShape(bottomRight = 40.dp),
                ambientColor = Color.Gray
            )
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .background(
                    color = Color(0xFFFFE585),
                    shape = AbsoluteCutCornerShape(bottomRight = 40.dp)
                )
                .padding(12.dp)
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Text(
                        notice.title,
                        fontWeight = FontWeight.Bold,
                        fontSize = 28.sp,
                        fontFamily = icesiminFontFamily
                    )
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            painterResource("drawable/eyes.xml"),
                            "",
                            modifier = Modifier.height(40.dp)
                        )
                        Text(notice.views.toString())
                    }
                }
                Divider()
                Text(notice.detail, fontSize = 24.sp, fontFamily = icejaramFontFamily)
            }
        }
    }
}