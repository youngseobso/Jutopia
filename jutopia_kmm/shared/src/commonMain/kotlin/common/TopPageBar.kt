package common

import Variables
import Variables.ColorsBackground
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.KeyboardArrowLeft
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import co.touchlab.kermit.Logger
import icehimchanFontFamily
import moe.tlaster.precompose.navigation.Navigator
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource

@OptIn(ExperimentalResourceApi::class)
@Composable
fun TopPageBar(navLoc: String, navigator: Navigator, showReturn: Boolean = true, showChatBot: Boolean = true, bgColor: Color = ColorsBackground) {

    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(0.dp)
            .fillMaxWidth()
            .height(80.dp)
            .background(color = bgColor)
            .padding(start = 20.dp, top = 16.dp, end = 40.dp, bottom = 16.dp)
    ) {
        Row (
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (showReturn)
            {
                IconButton(
                    onClick = { navigator.goBack() },
                ) {
                    Icon(
                        Icons.Outlined.KeyboardArrowLeft,
                        "뒤로가기"
                    )
                }
            } else {
                Spacer(modifier = Modifier.width(12.dp))
            }
            Text(navLoc, fontFamily = icehimchanFontFamily, fontSize = 28.sp)
        }
        if(showChatBot){
            Image(
                painterResource("drawable/text_bubble.xml"),
                null,
                modifier = Modifier.height(40.dp).clickable { navigator.navigate("/chatbot") }
            )
        }
    }
}