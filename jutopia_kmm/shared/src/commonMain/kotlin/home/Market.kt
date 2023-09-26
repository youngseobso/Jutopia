package home

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import common.TopPageBar
import moe.tlaster.precompose.navigation.Navigator

@Composable
fun Market(navigator: Navigator) {
    TopPageBar("상점", navigator=navigator)
}