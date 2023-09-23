package asset

import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import moe.tlaster.precompose.navigation.Navigator

@Composable
fun Asset(navigator: Navigator) {
    Text(text = "Asset")
    Button(onClick = {navigator.navigate("/home")}) {
        Text("gogo")
    }
}