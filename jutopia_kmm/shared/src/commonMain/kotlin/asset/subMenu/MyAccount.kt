package asset.subMenu

import UserInfo
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
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults.buttonColors
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import co.touchlab.kermit.Logger
import moe.tlaster.precompose.navigation.Navigator
import moe.tlaster.precompose.viewmodel.viewModel
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource

@Composable
fun MyAccount(navigator: Navigator, userInfo: UserInfo, viewModel: MyAccountViewModel = viewModel(modelClass = MyAccountViewModel::class) {
    MyAccountViewModel()
}) {

    val accountInformation by viewModel.accountInformation.collectAsState()
    val transactionHistory by viewModel.transactionHistory.collectAsState()

    Logger.d("입출금 내역 : $transactionHistory")

    val isLoadingAccount by viewModel.isLoadingAccount.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    if (isLoadingAccount && isLoading) viewModel.fetchData(userInfo.id)
    if (!isLoadingAccount && isLoading) viewModel.fetchHistory(accountInformation!!.uuid)

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
        AccountInfo(navigator, accountInformation!!)
        History(transactionHistory)
    }
}

@OptIn(ExperimentalResourceApi::class)
@Composable
fun AccountInfo(navigator: Navigator, accountInfo: AccountInformation) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp)
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
                Text(accountInfo.bank, color = ColorsOnPrimary.copy(alpha = 0.74F))
                Text("|", color = ColorsOnPrimary.copy(alpha = 0.74F))
                Text("입출금 통장", color = ColorsOnPrimary)
            }
            Row (
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Bottom,
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
            ) {
                Text("${addComma(accountInfo.balance.toDouble())}원", color = ColorsOnPrimary, fontSize = 36.sp)
                Button(
                    onClick = { navigator.navigate("/send") },
                    colors = buttonColors(
                        backgroundColor = ColorsPrimaryVariant
                    )
                ) {
                    Text("이체", color = ColorsOnPrimaryVariant)
                }
            }
        }
    }
}

@Composable
fun History(transactionHistory: List<DepositDetail>) {
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
        items(transactionHistory.reversed()) { detail ->
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
                    addComma(detail.amount) + "원",
                    fontSize = 20.sp,
                    color = if (detail.type == TransactionType.Deposit) Color(0xFFCB0B47) else Color(0xFF167BDF),
                    fontWeight = FontWeight.Bold
                )
            }
            Row(
                horizontalArrangement = Arrangement.End,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(addComma(detail.changes) + "원", color = Color(0xFF7B7B7B))
            }
        }
    }
}