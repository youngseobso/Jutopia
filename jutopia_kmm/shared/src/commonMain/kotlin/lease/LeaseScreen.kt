package lease

import BottomTabBar
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import common.TopPageBar
import moe.tlaster.precompose.navigation.Navigator

@Composable
fun LeaseScreen(navigator: Navigator) {
    Column {
        TopPageBar("임대")
        LeasePage()
    }
    BottomTabBar(navigator)
}

