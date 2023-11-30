package stock.stocktrade

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import common.TmpUserInfo
import stock.common.Stock
import stock.common.StockRequest

@Composable
@OptIn(ExperimentalComposeUiApi::class)
fun StockBuyingPage(
    stock: Stock,
    viewModel: StockTradeViewModel = moe.tlaster.precompose.viewmodel.viewModel(
        StockTradeViewModel::class
    ) { StockTradeViewModel(stock.id) }
) {

    var orderQuantity by remember { mutableStateOf("1") }
    val stockPrice = stock.nowMoney
    var orderPrice by remember { mutableStateOf("${stockPrice}") }
    val keyboardController = LocalSoftwareKeyboardController.current
    var showDialog by remember { mutableStateOf(false) }
    val tradeStatus by viewModel.tradeStatus.collectAsState()
    val myPoint by viewModel.myPoint.collectAsState()
    val totalAmount: Double =
        if (orderQuantity.isNotBlank() && orderPrice.isNotBlank()) {
            orderQuantity.toDouble().times(orderPrice.toDouble())
        } else {
            0.0
        }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(16.dp),
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        Text("보유 포인트: $myPoint")
        OutlinedTextField(
            value = orderQuantity,
            onValueChange = {
                if (it.all { char -> char.isDigit() }) {
                    orderQuantity = it
                }
            },
            label = { Text("주문량") },
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Done,
                keyboardType = KeyboardType.Number
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    keyboardController?.hide()
                }
            ),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(15.dp))

        val customColors = TextFieldDefaults.outlinedTextFieldColors(
            textColor = Color.Black,
            disabledTextColor = Color.Black,
            focusedBorderColor = Color.Blue,
            unfocusedBorderColor = Color.Blue,
            disabledBorderColor = Color.Blue
        )

        OutlinedTextField(
            value = orderPrice,
            onValueChange = {
            },
            label = { Text("주문가격") },
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Done,
                keyboardType = KeyboardType.Number
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    keyboardController?.hide()
                }
            ),
            modifier = Modifier.fillMaxWidth(),
            enabled = false,
            colors = customColors
        )

        Spacer(modifier = Modifier.height(16.dp))

        totalAmount?.let {
            Text(
                text = "총 주문 금액: ${it}",
                modifier = Modifier.fillMaxWidth()
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                showDialog = true
            },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Color(0xFFC3E0E8)
            )
        ) {
            Text(text = "구매")
        }
        if (showDialog) {
            AlertDialog(
                onDismissRequest = { showDialog = false },
                confirmButton = {
                    Button(
                        onClick = {
                            val request = StockRequest(
                                memberId = "",
                                stockId = stock.id,
                                type = TradeType.BUY,
                                volume = orderQuantity.toLong(),
                                price = orderPrice.toInt(),
                            )
                            viewModel.tradeStock(request)
                            orderQuantity = "1"
                            orderPrice = "${stock.nowMoney}"
                            showDialog = false
                        },
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = Color(0xFFC3E0E8)
                        )
                    ) {
                        Text("구매")
                    }
                },
                dismissButton = {
                    Button(
                        onClick = { showDialog = false }, colors = ButtonDefaults.buttonColors(
                            backgroundColor = Color(0xFFC3E0E8)
                        )
                    ) {
                        Text("취소")
                    }
                },
                title = { Text("구매 확인") },
                text = {
                    Column {
                        Text("${stock.stockName}")
                        Text("가격: ${orderPrice}")
                        Text("구매량: ${orderQuantity}")
                        Text("총 금액: ${totalAmount}")
                    }
                }
            )
        }

        if (tradeStatus == TradeStatus.SUCCESS) {
            AlertDialog(
                onDismissRequest = { viewModel.resetTradeStatus() },
                confirmButton = {
                    Button(
                        onClick = { viewModel.resetTradeStatus() },
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = Color(0xFFC3E0E8)
                        )
                    ) {
                        Text("확인")
                    }
                },
                title = { Text("구매 성공!") },
                text = {
                    Text("구매가 성공적으로 이루어졌습니다.")
                }
            )
        } else if (tradeStatus == TradeStatus.FAILURE) {
            AlertDialog(
                onDismissRequest = { viewModel.resetTradeStatus() },
                confirmButton = {
                    Button(
                        onClick = { viewModel.resetTradeStatus() },
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = Color(0xFFC3E0E8)
                        )
                    ) {
                        Text("확인")
                    }
                },
                title = { Text("구매 실패") },
                text = {
                    Text("구매가 실패했습니다.")
                }
            )
        }
    }
}
