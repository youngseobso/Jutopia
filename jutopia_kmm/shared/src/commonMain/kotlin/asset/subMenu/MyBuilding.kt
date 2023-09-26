package asset.subMenu

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import moe.tlaster.precompose.viewmodel.viewModel

@Composable
fun MyBuilding(viewModel: MyBuildingViewModel = viewModel(modelClass = MyBuildingViewModel::class) {
    MyBuildingViewModel()
}) {
    Text("Building")
}