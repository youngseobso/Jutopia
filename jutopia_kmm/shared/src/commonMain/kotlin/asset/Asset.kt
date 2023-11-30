package asset

import BottomTabBar
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ChipDefaults
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FilterChip
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import asset.subMenu.MyBuilding
import asset.subMenu.MyAccount
import asset.subMenu.MyPoint
import asset.subMenu.MySave
import asset.subMenu.MyStock
import common.TopPageBar
import io.github.xxfast.kstore.KStore
import io.github.xxfast.kstore.file.storeOf
import UserInfo
import Variables.ColorsPrimary
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import moe.tlaster.precompose.navigation.Navigator
import moe.tlaster.precompose.viewmodel.viewModel
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import pathTo

data class ChipItem(val idx: Int, val name: String, val bgColor: Color, val conColor: Color, val desc: String)

val chipItems: List<ChipItem> = listOf(
    ChipItem(0, "deposit", Color(0xFFF1D3FB), Color(0xFFAF30C9), "입출금"),
//    ChipItem(1, "save", Color(0xFFDFDBF9), Color(0xFF7E51D6), "적금"),
//    ChipItem(2, "point", Color(0xFFCBD8F2), Color(0xFF4963C7), "포인트"),
    ChipItem(3, "stock", Color(0xFFB7E7FF), Color(0xFF0087D1), "주식"),
//    ChipItem(4, "building", Color(0xFFC8EAC9), Color(0xFF358438), "부동산")
)

@Composable
fun Asset(navigator: Navigator, category: Int?, viewModel: AssetViewModel = viewModel(modelClass = AssetViewModel::class) {savedStateHolder ->
    AssetViewModel(savedStateHolder)
}) {

    val store: KStore<UserInfo> = storeOf(filePath = pathTo("user"))

    var userInfo by remember { mutableStateOf<UserInfo?>(null) }
    var isLoading by remember { mutableStateOf(true) }

    LaunchedEffect(true) {
        userInfo = store.get()
        isLoading = false
    }

    if (isLoading) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp)
        ) {
            CircularProgressIndicator(
                color = ColorsPrimary,
                backgroundColor = Color.LightGray,
                modifier = Modifier.width(64.dp)
            )
        }
    } else {
        if(category != null) viewModel.setChipIdx(category)

        Column {
            TopPageBar("자산", navigator, showReturn = false)

            Chips(navigator, viewModel.chipIdx.value, viewModel)

            when (viewModel.chipIdx.value) {
                0 -> MyAccount(navigator, userInfo!!)
                1 -> MySave()
                2 -> MyPoint()
                3 -> MyStock(userInfo!!, navigator)
                4 -> MyBuilding()
                else -> Text("Error Page")
            }
        }
        BottomTabBar(navigator, 1)
    }

}

@OptIn(ExperimentalMaterialApi::class, ExperimentalResourceApi::class)
@Composable
fun Chips(navigator: Navigator, selectedIdx: Int, viewModel: AssetViewModel ) {
    Box(
        modifier = Modifier
            .padding(12.dp)
    ) {
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(chipItems) { item ->
                FilterChip(
                    onClick = { viewModel.setChipIdx(item.idx) },
                    selected = false,
                    colors = ChipDefaults.filterChipColors(
                        backgroundColor = if (item.idx == viewModel.chipIdx.value) item.bgColor else item.bgColor.copy(alpha = 0.4F),
                        contentColor = if (item.idx == viewModel.chipIdx.value) item.conColor else item.conColor.copy(alpha = 0.4F)
                    ),
                    leadingIcon = {
                        Icon(
                            painter = painterResource("drawable/chip_" + item.name + ".xml"),
                            contentDescription = "chip_" + item.name
                        )
                    }
                ) {
                    Text(item.desc)
                }
            }
        }
    }
}