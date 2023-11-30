package asset.subMenu

import co.touchlab.kermit.Logger
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import moe.tlaster.precompose.viewmodel.ViewModel
import moe.tlaster.precompose.viewmodel.viewModelScope

class MyAccountViewModel: ViewModel() {
//    private val _transactionHistory: MutableList<DepositDetail> = mutableListOf(
//        DepositDetail("2023.09.07", "15:14:00", TransactionType.Withdrawal, "포인트 환전", 110000, 60000),
//        DepositDetail("2023.09.01", "09:00:00", TransactionType.Withdrawal, "임대료", 80000, 170000),
//        DepositDetail("2023.09.01", "00:00:00", TransactionType.Deposit, "월 기본급", 200000, 250000)
//    )
//
//    val transactionHistory: List<DepositDetail> = _transactionHistory

    private val _transactionHistory: MutableStateFlow<List<DepositDetail>> = MutableStateFlow(listOf())

    val transactionHistory: StateFlow<List<DepositDetail>> = _transactionHistory

    private val _accountInformation: MutableStateFlow<AccountInformation?> = MutableStateFlow(null)

    val accountInformation: StateFlow<AccountInformation?> = _accountInformation

    private val _isLoadingAccount: MutableStateFlow<Boolean> = MutableStateFlow(true)

    val isLoadingAccount: StateFlow<Boolean> = _isLoadingAccount

    private val _isLoading: MutableStateFlow<Boolean> = MutableStateFlow(true)

    val isLoading: StateFlow<Boolean> = _isLoading

    fun fetchData(userId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _accountInformation.emit(MyAccountAPI().getAccountInfo(userId))
            _isLoadingAccount.emit(false)
        }
    }

    fun fetchHistory(uuid: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _transactionHistory.emit(MyAccountAPI().getAccoutHistory(uuid))
            Logger.d("junwhan's uuid ${uuid}")
            _isLoading.emit(false)
        }
    }
}