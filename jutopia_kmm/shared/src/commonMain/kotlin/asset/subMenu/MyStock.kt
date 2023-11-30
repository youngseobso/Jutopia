package asset.subMenu

import UserInfo
import Variables.ColorsOnPrimary
import Variables.ColorsOnSecondary
import Variables.ColorsPrimary
import Variables.ColorsPrimaryVariant
import Variables.ColorsSecondary
import addComma
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.Button
import androidx.compose.material.Chip
import androidx.compose.material.ChipColors
import androidx.compose.material.ChipDefaults
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import moe.tlaster.precompose.navigation.Navigator
import moe.tlaster.precompose.viewmodel.viewModel
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource

@Composable
fun MyStock(userInfo: UserInfo, navigator: Navigator, viewModel: MyStockViewModel = viewModel(modelClass = MyStockViewModel::class) {
    MyStockViewModel()
}) {
    val ownedStock by viewModel.ownedStock.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    if (isLoading) viewModel.fetchData(userInfo.uuid)

    if(isLoading) {
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
        if(ownedStock.isEmpty()) {
            NoStock(navigator)
        } else {
            StockInfo(ownedStock)
            Content(ownedStock)
        }
    }
}

@OptIn(ExperimentalResourceApi::class, ExperimentalMaterialApi::class)
@Composable
fun NoStock(navigator: Navigator) {
    Column (
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(12.dp, Alignment.Top)
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .background(ColorsPrimary)
                .padding(8.dp)
        ) {
            Image(
                painterResource("drawable/coin_rabbit.png"),
                null,
                modifier = Modifier.width(24.dp)
            )
            Text("SSAFY 증권", color = ColorsOnPrimary.copy(alpha = 0.74F))
        }
        Text("보유하고 있는 주식이 없습니다.")
        Chip(
            onClick = { navigator.navigate("/stocklist") },
            leadingIcon = { Icon(Icons.Default.Add, "", tint = ColorsOnSecondary)},
            colors = ChipDefaults.chipColors(backgroundColor = ColorsSecondary),
        ) {
            Box(
                modifier = Modifier
                    .padding(12.dp)
            ) {
                Text("주식 매수", color = ColorsOnSecondary, fontSize = 16.sp)
            }
        }
    }
}

@OptIn(ExperimentalResourceApi::class)
@Composable
fun StockInfo(ownedStock: List<StockDetail>) {
    val totalBought = ownedStock.map { it.bought * it.qty }.reduce {acc, i -> acc + i }
    val totalCurrent = ownedStock.map { it.current * it.qty }.reduce {acc, i -> acc + i }

    val totalDiff = totalCurrent - totalBought
    val diffPercent = totalDiff / totalBought * 100


    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(108.dp)
            .background(ColorsPrimary)
            .padding(12.dp)
    ) {
        Column {
            Row(
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painterResource("drawable/coin_rabbit.png"),
                    null,
                    modifier = Modifier.width(24.dp)
                )
                Text("SSAFY 증권", color = ColorsOnPrimary.copy(alpha = 0.74F))
            }
            Row (
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text("총 손익", color = ColorsOnPrimary, fontSize = 24.sp)
                Row (
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .width(160.dp)
                ) {
                    Text("${addComma(totalDiff.toDouble())}P", color = ColorsOnPrimary, fontSize = 24.sp)
                    Text("${diffPercent}%", color = ColorsOnPrimary)
                }
            }
            Row (
                horizontalArrangement = Arrangement.spacedBy(20.dp),
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Row (
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .weight(1f)
                ) {
                    Text("총 매입", color = ColorsOnPrimary.copy(alpha = 0.74F))
                    Text("${addComma(totalBought.toDouble())}P", color = ColorsOnPrimary.copy(alpha = 0.74F))
                }

                Row (
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .weight(1f)
                ) {
                    Text("총 평가", color = ColorsOnPrimary.copy(alpha = 0.74F))
                    Text("${addComma(totalCurrent.toDouble())}P", color = ColorsOnPrimary.copy(alpha = 0.74F))
                }
            }
        }
    }
}

@Composable
fun Content(ownedStock: List<StockDetail>)
{
    LazyRow {
        item {
            Column (
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {
                TableHeaders()
                ownedStock.map { stockDetail ->
                    TableItems(stockDetail.name, stockDetail.bought, stockDetail.current, stockDetail.qty, stockDetail.rate, stockDetail.changes)
                }
            }
        }
    }

}

@Composable
fun TableHeaders() {
    val headerTexts = listOf("종목명", "매입가", "평가손익", "수익률", "현재금액", "매입금액", "평가금액")

    Row (
        horizontalArrangement = Arrangement.Start
    ) {
        headerTexts.map {
            TableHeader(it)
        }
    }
}

@Composable
fun TableHeader(text: String) {
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .background(color = Color(0xFFEDEDED))
            .width(92.dp)
    ) {
        Text(text)
    }
}

@Composable
fun TableItems(name: String, bought: Int, current: Int, qty: Int, rate: Double, changes: Comparison) {

    val boughtValue = bought * qty
    val currentValue = current * qty
    val profit: Int = currentValue - boughtValue

    Row {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .width(92.dp)
                .border(width = 0.5.dp, color = Color.LightGray)
                .height(40.dp)
                .padding(4.dp)
        ) {
            Text(name, overflow = TextOverflow.Ellipsis)
        }
        Box(
            contentAlignment = Alignment.CenterEnd,
            modifier = Modifier
                .width(92.dp)
                .border(width = 0.5.dp, color = Color.LightGray)
                .height(40.dp)
                .padding(4.dp)
        ) {
            Text(addComma(bought.toDouble()), overflow = TextOverflow.Ellipsis)
        }
        Box(
            contentAlignment = Alignment.CenterEnd,
            modifier = Modifier
                .width(92.dp)
                .border(width = 0.5.dp, color = Color.LightGray)
                .height(40.dp)
                .padding(4.dp)
        ) {
            Text(addComma(profit.toDouble()), overflow = TextOverflow.Ellipsis, color = if(changes == Comparison.Increased) Color(0xFFCB0B47) else if(changes == Comparison.Decreased) Color(0xFF167BDF) else Color.Black)
        }
        Box(
            contentAlignment = Alignment.CenterEnd,
            modifier = Modifier
                .width(92.dp)
                .border(width = 0.5.dp, color = Color.LightGray)
                .height(40.dp)
                .padding(4.dp)
        ) {
            Text("$rate%", overflow = TextOverflow.Ellipsis, color = if(changes == Comparison.Increased) Color(0xFFCB0B47) else if(changes == Comparison.Decreased) Color(0xFF167BDF) else Color.Black)
        }
        Box(
            contentAlignment = Alignment.CenterEnd,
            modifier = Modifier
                .width(92.dp)
                .border(width = 0.5.dp, color = Color.LightGray)
                .height(40.dp)
                .padding(4.dp)
        ) {
            Text(addComma(current.toDouble()), overflow = TextOverflow.Ellipsis)
        }
        Box(
            contentAlignment = Alignment.CenterEnd,
            modifier = Modifier
                .width(92.dp)
                .border(width = 0.5.dp, color = Color.LightGray)
                .height(40.dp)
                .padding(4.dp)
        ) {
            Text(addComma(boughtValue.toDouble()), overflow = TextOverflow.Ellipsis)
        }
        Box(
            contentAlignment = Alignment.CenterEnd,
            modifier = Modifier
                .width(92.dp)
                .border(width = 0.5.dp, color = Color.LightGray)
                .height(40.dp)
                .padding(4.dp)
        ) {
            Text(addComma(currentValue.toDouble()), overflow = TextOverflow.Ellipsis)
        }
    }
}