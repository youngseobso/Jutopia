package asset.subMenu

import moe.tlaster.precompose.viewmodel.ViewModel

class MySaveViewModel: ViewModel() {
    private val _transactionHistory: MutableList<depositDetail> = mutableListOf(
        depositDetail("2023.09.07", "15:14:00", transactionType.Deposit, "1회차 입금", 10000, 10000),
    )

    val transactionHistory: List<depositDetail> = _transactionHistory
}