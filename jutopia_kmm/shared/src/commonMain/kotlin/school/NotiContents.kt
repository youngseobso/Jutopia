package school

import BottomTabBar
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import common.TopPageBar
import moe.tlaster.precompose.navigation.Navigator

@Composable
fun NotiContents(navigator: Navigator, idx: Int) {
    Column {
        TopPageBar("공지사항 상세", navigator)


    }
    BottomTabBar(navigator)
}

@Composable
fun Contents() {

}