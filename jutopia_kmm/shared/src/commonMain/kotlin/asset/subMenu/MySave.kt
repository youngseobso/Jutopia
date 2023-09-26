package asset.subMenu

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import moe.tlaster.precompose.viewmodel.viewModel

@Composable
fun MySave(viewModel: MySaveViewModel = viewModel(modelClass = MySaveViewModel::class) {
    MySaveViewModel()
}) {
    Text("Save")
}