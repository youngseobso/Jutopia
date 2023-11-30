package stock.common

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import formatDouble

@Composable
fun StockRow(stock: Stock, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .clickable { onClick() },
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(stock.stockName, fontSize = 20.sp)
        Column(
            horizontalAlignment = Alignment.End,
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text("${stock.nowMoney}", fontSize = 20.sp)
                val arrowAndColor =
                    if (stock.type!! == 1) "↑" to Color.Red else if (stock.type!! == -1) "↓" to Color.Blue else "-" to Color.Gray
                Text(arrowAndColor.first, color = arrowAndColor.second, fontSize = 20.sp)
                Text(
                    "${formatDouble(stock.changeRate!!, 3)}%",
                    color = arrowAndColor.second,
                    fontSize = 20.sp
                )
            }
            val changeColor = if (stock.type!! == 1)  Color.Red else if (stock.type!! == -1) Color.Blue else Color.Gray
            Text("${stock.changeMoney}", color = changeColor, fontSize = 14.sp)
        }
    }
}