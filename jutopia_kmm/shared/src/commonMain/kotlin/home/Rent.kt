package home

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import lease.LeaseScreen
import moe.tlaster.precompose.navigation.Navigator

@Composable
fun Rent(navigator: Navigator) {
    LeaseScreen(navigator)
}