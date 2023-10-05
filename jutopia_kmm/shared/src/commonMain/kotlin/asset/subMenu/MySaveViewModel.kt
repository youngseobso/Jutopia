package asset.subMenu

import moe.tlaster.precompose.viewmodel.ViewModel

class MySaveViewModel: ViewModel() {
    private val _transactionHistory: MutableList<DepositDetail> = mutableListOf(
        DepositDetail("2023.09.07", "15:14:00", TransactionType.Deposit, "1회차 입금", 10000.0, 10000.0),
    )

    val transactionHistory: List<DepositDetail> = _transactionHistory
}