package asset.subMenu

import Variables.ColorsOnPrimary
import Variables.ColorsOnPrimaryVariant
import Variables.ColorsPrimary
import Variables.ColorsPrimaryVariant
import addComma
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import co.touchlab.kermit.Logger
import moe.tlaster.precompose.viewmodel.viewModel
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource

@Composable
fun MySave(viewModel: MySaveViewModel = viewModel(modelClass = MySaveViewModel::class) {
    MySaveViewModel()
}) {
    SaveInfo()
    History(viewModel)
}

@OptIn(ExperimentalResourceApi::class)
@Composable
fun SaveInfo() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(140.dp)
            .background(ColorsPrimary)
            .padding(12.dp)
    ) {
        Column {
            Row (
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painterResource("drawable/coin_rabbit.png"),
                        null,
                        modifier = Modifier.width(24.dp)
                    )
                    Text("양 많이요 적금", color = ColorsOnPrimary.copy(alpha = 0.74F))
                    Text("|", color = ColorsOnPrimary.copy(alpha = 0.74F))
                    Text("입출금 통장", color = ColorsOnPrimary)
                }
                Button(
                    onClick = {Logger.d("해지기능")},
                    colors = ButtonDefaults.buttonColors(backgroundColor = ColorsPrimaryVariant, contentColor = ColorsOnPrimaryVariant),
                    contentPadding = PaddingValues(1.dp)
                ) {
                    Text("해지하기")
                }
            }
            Row (
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Bottom,
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
            ) {
                Column {
                    Text("60,000원", color = ColorsOnPrimary, fontSize = 36.sp, overflow = TextOverflow.Ellipsis)
                    Text("만기일 2023.10.01", color = ColorsOnPrimary, fontSize = 12.sp)
                }
                Column (
                    modifier = Modifier
                        .width(140.dp)
                ) {
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        Text("적용 이율", color = ColorsOnPrimary, fontSize = 12.sp)
                        Text("주 10%", color = ColorsOnPrimary, fontSize = 12.sp)
                    }
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        Text("입금 회차", color = ColorsOnPrimary, fontSize = 12.sp)
                        Text("1", color = ColorsOnPrimary, fontSize = 12.sp)
                    }
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        Text("예상수령금액", color = ColorsOnPrimary, fontSize = 12.sp)
                        Text("51,051원", color = ColorsOnPrimary, fontSize = 12.sp, overflow = TextOverflow.Ellipsis)
                    }
                }
            }
        }
    }
}

@Composable
fun History(viewModel: MySaveViewModel) {
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                top = 20.dp,
                start = 20.dp,
                end = 20.dp,
                bottom = 80.dp,
            ),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        var prev = ""
        items(viewModel.transactionHistory) { detail ->
            if (prev != detail.date) {
                prev = detail.date

                Text(detail.date, fontWeight = FontWeight.Bold)
                Divider(thickness = 2.dp)
            } else {
                Divider()
            }
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(detail.time, color = Color(0xFF7B7B7B))
                Text(
                    if (detail.type == TransactionType.Deposit) "입금" else "출금",
                    color = Color(0xFF7B7B7B)
                )
            }
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(detail.memo, fontSize = 20.sp, fontWeight = FontWeight.Bold)
                Text(
                    addComma(detail.amount.toDouble()) + "원",
                    fontSize = 20.sp,
                    color = if (detail.type == TransactionType.Deposit) Color(0xFFCB0B47) else Color(
                        0xFF167BDF
                    ),
                    fontWeight = FontWeight.Bold
                )
            }
            Row(
                horizontalArrangement = Arrangement.End,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(addComma(detail.changes.toDouble()) + "원", color = Color(0xFF7B7B7B))
            }
        }
    }
}