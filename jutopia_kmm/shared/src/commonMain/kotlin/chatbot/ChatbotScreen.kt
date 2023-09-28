package chatbot

import BottomTabBar
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import common.TopPageBar
import moe.tlaster.precompose.navigation.Navigator

@Composable
fun ChatbotScreen(navigator: Navigator) {
    Column {
        TopPageBar("임대", navigator = navigator)
        ChatbotPage()
    }
    BottomTabBar(navigator)
}

