package asset.subMenu

enum class transactionType {
    Deposit,
    Withdrawal
}

data class depositDetail(val date: String, val time: String, val type: transactionType, val memo: String, val amount: Number, val changes: Number )