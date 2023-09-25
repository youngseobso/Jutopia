package menus

import BottomTabBar
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import moe.tlaster.precompose.navigation.Navigator

@Composable
fun Menus(navigator: Navigator) {
    Column {
        BottomTabBar(navigator)
    }
}