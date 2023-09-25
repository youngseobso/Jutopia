package asset

import BottomTabBar
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import common.TopPageBar
import moe.tlaster.precompose.navigation.Navigator

@Composable
fun Asset(navigator: Navigator) {
    Column {
        TopPageBar("자산")
        BottomTabBar(navigator)
    }
}