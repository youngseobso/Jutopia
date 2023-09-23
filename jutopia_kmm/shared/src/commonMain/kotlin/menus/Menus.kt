package menus

import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import moe.tlaster.precompose.navigation.Navigator

@Composable
fun Menus(navigator: Navigator) {
    Text(text = "Menus")
    Button(onClick = {navigator.navigate("/home")}) {
        Text("gogo")
    }
}