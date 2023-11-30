package landing

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import icesiminFontFamily
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import moe.tlaster.precompose.navigation.Navigator
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource

@OptIn(ExperimentalResourceApi::class)
@Composable
fun Landing(navigator: Navigator) {
    val delayMillis = 5000

    val coroutineScope = rememberCoroutineScope()

    coroutineScope.launch {
        runBlocking {
            launch {
                delay(delayMillis.toLong())
                navigator.navigate("/mainpage")
            }
        }
    }



    Column {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(8f)
                .background(color = Color(0xFFBDEBFF))
        ) {
            Column (
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxSize()
            ) {
                Column (
                    modifier = Modifier
                        .padding(40.dp)
                ) {
                    Row {
                        Text("학교", color = Color(0xFF7E51D6), fontSize = 20.sp)
                        Text("에서 배우는", color = Color.Black, fontSize = 20.sp)
                    }
                    Row {
                        Text("경제", color = Color(0xFFECC106), fontSize = 20.sp)
                        Text("상식", color = Color.Black, fontSize = 20.sp)
                    }
                }
                Row (
                    horizontalArrangement = Arrangement.End,
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Box (
                        modifier = Modifier
                            .padding(end = 12.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.Bottom
                        ) {
                            Text(
                                "주",
                                fontSize = 80.sp,
                                fontFamily = icesiminFontFamily,
                                color = Color(0xFFFF5722)
                            )
                            Text(
                                "토피아",
                                fontSize = 40.sp,
                                fontFamily = icesiminFontFamily,
                                color = Color(0xFF0087D1)
                            )
                        }
                    }
                }
                Row (
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Bottom,
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Box (
                        modifier = Modifier
                            .padding(start = 24.dp)
                    ) {
                        Image(painter = painterResource("drawable/walking.xml"), "")
                    }
                    Box (
                        modifier = Modifier
                            .padding(end = 12.dp)
                    ) {
                        Image(painter = painterResource("drawable/school.png"), "")
                    }
                }
            }
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(2f)
                .background(color = Color.White)
        ) {

        }
    }
}