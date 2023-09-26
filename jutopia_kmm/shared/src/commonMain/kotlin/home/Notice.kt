package home

import BottomTabBar
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import common.TopPageBar
import moe.tlaster.precompose.navigation.Navigator

@Composable
fun Notice(navigator: Navigator) {
    Column {
        TopPageBar("공지사항")
    }
    BottomTabBar(navigator)

}