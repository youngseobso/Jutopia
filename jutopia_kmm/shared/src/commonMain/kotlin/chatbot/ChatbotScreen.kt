package chatbot

import BottomTabBar
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import common.TopPageBar
import moe.tlaster.precompose.navigation.Navigator

@Composable
fun ChatbotScreen(navigator: Navigator) {
    Column(modifier = Modifier.padding(bottom = 70.dp).fillMaxHeight()) {
        TopPageBar("챗봇", navigator = navigator)
        ChatbotPage()
    }
    BottomTabBar(navigator, 0)
}

