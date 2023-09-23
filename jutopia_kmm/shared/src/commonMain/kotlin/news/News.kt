package news

import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import moe.tlaster.precompose.navigation.Navigator

@Composable
fun News(navigator: Navigator) {
    Text(text = "News")
    Button(onClick = {navigator.navigate("/home")}) {
        Text("gogo")
    }
}