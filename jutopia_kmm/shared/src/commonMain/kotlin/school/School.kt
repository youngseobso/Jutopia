package school

import BottomTabBar
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import common.TopPageBar
import moe.tlaster.precompose.navigation.Navigator

@Composable
fun School(navigator: Navigator) {
    Column {
        TopPageBar("학교")
        BottomTabBar(navigator)
    }
}