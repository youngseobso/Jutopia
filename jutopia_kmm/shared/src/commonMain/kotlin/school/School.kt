package school

import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import moe.tlaster.precompose.navigation.Navigator

@Composable
fun School(navigator: Navigator) {
    Text(text = "School")
    Button(onClick = {navigator.navigate("/home")}) {
        Text("gogo")
    }
}