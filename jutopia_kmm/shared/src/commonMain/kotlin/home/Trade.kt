package home

import BottomTabBar
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import common.TopPageBar
import moe.tlaster.precompose.navigation.Navigator

@Composable
fun Trade(navigator: Navigator) {
    Column {
        TopPageBar("환전")
    }
    BottomTabBar(navigator)
}