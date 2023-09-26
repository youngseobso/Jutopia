package home

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import common.TopPageBar
import moe.tlaster.precompose.navigation.Navigator

@Composable
fun Stock(navigator: Navigator) {
    TopPageBar("주식", navigator=navigator)
    navigator.navigate("/stocklist")
}