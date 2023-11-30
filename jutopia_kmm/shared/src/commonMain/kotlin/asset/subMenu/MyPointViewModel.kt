package asset.subMenu

import moe.tlaster.precompose.viewmodel.ViewModel

class MyPointViewModel: ViewModel() {
    private val _transactionHistory: MutableList<DepositDetail> = mutableListOf(
        DepositDetail("2023.09.07", "15:14:00", TransactionType.Withdrawal, "포인트 환전", 110000.0, 60000.0),
        DepositDetail("2023.09.01", "09:00:00", TransactionType.Withdrawal, "임대료", 80000.0, 170000.0),
        DepositDetail("2023.09.01", "00:00:00", TransactionType.Deposit, "월 기본급", 200000.0, 250000.0)
    )

    val transactionHistory: List<DepositDetail> = _transactionHistory
}