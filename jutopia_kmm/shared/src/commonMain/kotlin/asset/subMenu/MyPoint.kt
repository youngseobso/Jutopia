package asset.subMenu

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import moe.tlaster.precompose.viewmodel.viewModel

@Composable
fun MyPoint(viewModel: MyPointViewModel = viewModel(modelClass = MyPointViewModel::class) {
    MyPointViewModel()
}) {
    Text("Point")
}