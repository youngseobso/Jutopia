import Sign.MainPage
import Sign.SignUp
import Sign.StudentSignUp
import Sign.TeacherSignUp
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import asset.Asset
import chatbot.ChatbotScreen
import com.svenjacobs.reveal.RevealCanvas
import com.svenjacobs.reveal.rememberRevealCanvasState
import home.Bank
import moe.tlaster.precompose.navigation.NavHost
import moe.tlaster.precompose.navigation.rememberNavigator
import org.jetbrains.compose.resources.ExperimentalResourceApi
import home.Home
import home.Market
import home.Notice
import home.Rent
import home.Save
import home.Send
import home.Send_detail
import home.Stock
import home.Trade
import landing.Landing
import lease.LeaseScreen
import menus.ChangePassword
import menus.Menus
import moe.tlaster.precompose.navigation.path
import moe.tlaster.precompose.navigation.transition.NavTransition
import news.News
import school.NotiContents
import school.School
import stock.common.StockScreen
import stock.stockchart.StockChartScreen
import stock.stocklist.StockListScreen


@OptIn(ExperimentalResourceApi::class)
@Composable
fun App() {
    val revealCanvasState = rememberRevealCanvasState()
    val navigator = rememberNavigator()
    MaterialTheme {
        RevealCanvas(
            modifier = Modifier.fillMaxSize(),
            revealCanvasState = revealCanvasState,
        ) {
            NavHost(
                navigator = navigator,
                navTransition = NavTransition(
                    createTransition = EnterTransition.None,
                    destroyTransition = ExitTransition.None,
                    pauseTransition = ExitTransition.None,
                    resumeTransition = EnterTransition.None
                ),
                initialRoute = "/landing",
            ) {
                scene(
                    route = "/landing"
                ) {
                    Landing(navigator)
                }
                scene(
                    route = "/mainpage"
                ) {
                    MainPage(navigator)
                }
                scene(
                    route = "/home"
                ) {
                    Home(navigator)
                }
                scene(
                    route = "/asset/{category}?"
                ) {
                    val category: Int? = it.path<Int>("category")
                    Asset(navigator, category)
                }
                scene(
                    route = "/school"
                ) {
                    School(navigator)
                }
                scene(
                    route = "/notice/{idx}?"
                ) {
                    val idx: Int? = it.path<Int>("idx")
                    NotiContents(navigator, idx!!)
                }
                scene(
                    route = "/news"
                ) {
                    News(navigator, revealCanvasState)
                }
                scene(
                    route = "/menus"
                ) {
                    Menus(navigator)
                }
                scene(
                    route = "/changepassword"
                ) {
                    ChangePassword(navigator)
                }
                scene(
                    route = "/bank"
                ) {
                    Bank(navigator)
                }
                scene(
                    route = "/stock"
                ) {
                    Stock(navigator)
                }
                scene(
                    route = "/rent"
                ) {
                    Rent(navigator)
                }
                scene(
                    route = "/trade"
                ) {
                    Trade(navigator)
                }
                scene(
                    route = "/market"
                ) {
                    Market(navigator)
                }
                scene(
                    route = "/notice"
                ) {
                    Notice(navigator)
                }
                scene(
                    route = "/lease"
                ) {
                    LeaseScreen(navigator)
                }
                scene(
                    route = "/stocklist"
                ) {
                    StockListScreen(navigator)
                }
                scene(
                    route = "/stockChart/{stockId}/{stockCode}?"
                ) { backStackEntry ->
                    val stockId: String? = backStackEntry.path<String>("stockId")
                    val stockCode: String? = backStackEntry.path<String>("stockCode")
                    StockScreen(stockId!!,stockCode!!, navigator)
                }
                scene(
                    route = "/stocktrade/{stockId}/{stockCode}?"
                ) { backStackEntry ->
                    val stockId: String? = backStackEntry.path<String>("stockId")
                    val stockCode: String? = backStackEntry.path<String>("stockCode")
                    StockScreen(stockId!!,stockCode!!, navigator)
                }
                scene(
                    route = "/send"
                ) {
                    Send(navigator)
                }
                scene(
                    route = "/save"
                ) {
                    Save(navigator, revealCanvasState = revealCanvasState)
                }
                scene(
                    route = "/send_detail/{studentName}/{studentNumber}"
                ) { backStackEntry ->
                    val studentName: String? = backStackEntry.path<String>("studentName")
                    val studentNumber: Int? = backStackEntry.path<String>("studentNumber")?.toIntOrNull()
                    Send_detail(navigator, studentName!!, studentNumber!!)
                }
                scene(
                    route = "/chatbot"
                ) {
                    ChatbotScreen(navigator)
                }
                scene(
                    route = "/signup"
                ) {
                    SignUp(navigator)
                }
                scene(
                    route = "/studentsignup/{id}/{password}"
                ) { backStackEntry ->
                    val id = backStackEntry.path<String>("id")
                    val password = backStackEntry.path<String>("password")

                    StudentSignUp(navigator, id, password)
                }
                scene(
                    route = "/teachersignup/{id}/{password}"
                ) { backStackEntry ->
                    val id = backStackEntry.path<String>("id")
                    val password = backStackEntry.path<String>("password")

                    TeacherSignUp(navigator, id, password)
                }
            }
        }
    }
}

expect fun getPlatformName(): String

expect val icehimchanFontFamily: FontFamily
expect val icejaramFontFamily: FontFamily
expect val icesiminFontFamily: FontFamily
expect val icesotongFontFamily: FontFamily
expect val pretendardFontFamily: FontFamily
expect fun formatDouble(value: Double, decimalPlaces: Int): String
expect fun addComma(value: Double): String

expect fun openUrl(url: String?)

expect fun pathTo(id: String): String