package home

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import common.TopPageBar
import moe.tlaster.precompose.navigation.Navigator

@Composable
fun Rent(navigator: Navigator) {
    TopPageBar("임대", navigator=navigator)
}