@file:OptIn(ExperimentalResourceApi::class)

import Variables.ColorsOnPrimary
import Variables.ColorsPrimary
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.TabRowDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import moe.tlaster.precompose.navigation.NavOptions
import moe.tlaster.precompose.navigation.Navigator
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource

data class MainTabItem(val idx: Int, val nav: String, val imgSrc: String, val tabName: String)

@Composable
fun BottomTabBar(navigator: Navigator, idx: Int) {


    val mainTabItems: List<MainTabItem> = listOf(
        MainTabItem(idx = 0, nav = "/home", imgSrc = "drawable/house.xml", tabName = "홈"),
        MainTabItem(idx = 1, nav = "/asset", imgSrc = "drawable/creditcard.xml", tabName = "자산"),
        MainTabItem(idx = 2, nav = "/school", imgSrc = "drawable/graduationcap.xml", tabName = "학교"),
        MainTabItem(idx = 3, nav = "/news", imgSrc = "drawable/newspaper.xml", tabName = "뉴스"),
        MainTabItem(idx = 4, nav = "/menus", imgSrc = "drawable/list_bullet.xml", tabName = "메뉴"),
    )

    Column (
        verticalArrangement = Arrangement.spacedBy(0.dp, Alignment.Bottom),
        modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth()
            .padding(0.dp)
    ) {
        TabRow(
            selectedTabIndex = idx,
            backgroundColor = ColorsPrimary,
            contentColor = ColorsOnPrimary,
            indicator = {TabRowDefaults.Indicator(color = ColorsPrimary.copy(alpha = 0.0f))},
            modifier = Modifier
                .fillMaxWidth()
                .height(72.dp)
        ) {
            mainTabItems.map {tabItem ->

                val isSelected: Boolean = idx == tabItem.idx

                Tab(
                    selected = isSelected,
                    onClick = {
                        navigator.navigate(
                            tabItem.nav,
                            NavOptions(
                                launchSingleTop = true
                            )
                        )
                    }
                ) {
                    Image(
                        painterResource(tabItem.imgSrc),
                        "",
                        alpha = if (isSelected) 1f else 0.4f,
                        modifier = Modifier.height(40.dp)
                    )
                    Text(tabItem.tabName)
                }
            }
        }
//        Box(
//            modifier = Modifier
//                .fillMaxWidth()
//                .height(20.dp)
//                .background(color = ColorsPrimary)
//        )
    }
}