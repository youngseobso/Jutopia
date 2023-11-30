package home

import BottomTabBar
import UserInfo
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp
import common.TopPageBar
import io.github.xxfast.kstore.KStore
import io.github.xxfast.kstore.file.storeOf
import moe.tlaster.precompose.navigation.Navigator
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import pathTo

val deepBlue = Color(0xFF3F51B5)
val lightBlue = Color(0xFF5D7DD4)
val lightGray = Color(0xFFF6F6F6)
@OptIn(ExperimentalResourceApi::class)
@Composable
fun Bank(navigator: Navigator) {
    var userName by remember { mutableStateOf("") }
    val store: KStore<UserInfo> = storeOf(filePath = pathTo("user"))
    LaunchedEffect(1) {
        val temp: UserInfo? = store.get()
        if (temp != null) {
            userName = temp.name
        }
    }

    var humanImg = "drawable/human.xml"
    var sendImg = "drawable/send.xml"
    var saveImg = "drawable/save.xml"
    var dealImg = "drawable/deal.xml"

    val humanIcon: Painter = painterResource(humanImg)
    val sendIcon: Painter = painterResource(sendImg)
    val saveIcon: Painter = painterResource(saveImg)
    val dealIcon: Painter = painterResource(dealImg)

    var selectedTab by remember { mutableStateOf(0) }

    Column(

    ) {
        TopPageBar("은행", navigator=navigator)
        Box(
            modifier = Modifier
                .fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Box(
                modifier = Modifier
                    .width(300.dp)
                    .height(200.dp)
                    .background(lightBlue),

                ) {
                Column {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp)
                            .padding(top = 20.dp)
                            .background(deepBlue)

                    ) {
                        Text(
                            "이번 달 수입 & 지출",
                            modifier = Modifier.align(Alignment.Center),
                            color = Color.White
                        )
                    }
                    Row(
                        modifier = Modifier
                            .padding(top = 15.dp)
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                    ) {
                        Box {
                            Text("<2023.09월>",color = Color.White)
                        }
                        Row(
                            modifier = Modifier
                                .padding(start = 10.dp)
                        ) {
                            Image(painter = humanIcon, contentDescription = "Human Icon")
                            Spacer(modifier = Modifier.width(10.dp))
                            Text(userName,color = Color.White)
                        }
                    }

                    Row(
                        modifier = Modifier
                            .padding(top = 15.dp)
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text("수입",color = Color.White)
                            Text("1,000,000₩",color = Color.White)
                        }

                        Box(
                            modifier = Modifier
                                .width(3.dp)
                                .height(50.dp)
                                .background(deepBlue)
                        )

                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text("지출",color = Color.White)
                            Text("520,000₩",color = Color.White)
                        }
                    }

                }

            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 60.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
        ) {
            Box(
                modifier = Modifier
                    .width(80.dp)
                    .height(96.dp)
                    .background(lightGray)
                    .clickable { selectedTab = 1 },
                contentAlignment = Alignment.Center

            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement= Arrangement.spacedBy(10.dp)
                ){
                    Image(painter = sendIcon, contentDescription = "Send Icon")
                    Text("송금하기")
                }
            }

            Box(
                modifier = Modifier
                    .width(80.dp)
                    .height(96.dp)
                    .background(lightGray)
                    .clickable { selectedTab = 2 },
                contentAlignment = Alignment.Center

            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement= Arrangement.spacedBy(10.dp)
                ){
                    Image(painter = saveIcon, contentDescription = "Save Icon")
                    Text("적금상품")
                }
            }

            Box(
                modifier = Modifier
                    .width(80.dp)
                    .height(96.dp)
                    .background(lightGray)
                    .clickable { selectedTab = 3 },
                contentAlignment = Alignment.Center

            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement= Arrangement.spacedBy(10.dp)
                ){
                    Image(painter = dealIcon, contentDescription = "Deal Icon")
                    Text("거래내역")
                }
            }
        }

        when (selectedTab) {
            1 -> {
                navigator.navigate("/send")
            }
            2 -> {
                navigator.navigate("/save")
            }
            3 -> {
                navigator.navigate("/asset")
            }
        }
        BottomTabBar(navigator, 0)
    }
}