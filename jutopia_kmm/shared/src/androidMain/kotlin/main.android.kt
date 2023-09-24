import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import com.myapplication.common.R

actual fun getPlatformName(): String = "Android"

@Composable fun MainView() = App()

@Composable
fun test() {
    Text("test STring", fontFamily = FontFamily(
        Font(R.font.icejaram)
    ))
}