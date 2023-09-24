package news

import BottomTabBar
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import moe.tlaster.precompose.navigation.Navigator

@Composable
fun News(navigator: Navigator) {
    Column {
        BottomTabBar(navigator)
    }
}