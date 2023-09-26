package asset.subMenu

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import moe.tlaster.precompose.viewmodel.viewModel

@Composable
fun MyStock(viewModel: MyStockViewModel = viewModel(modelClass = MyStockViewModel::class) {
    MyStockViewModel()
}) {
    Text("Stock")
}