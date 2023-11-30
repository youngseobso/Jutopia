package asset.subMenu

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

enum class TransactionType {
    Deposit,
    Withdrawal
}

data class DepositDetail(val date: String, val time: String, val type: TransactionType, val memo: String, val amount: Double, val changes: Double )

data class AccountInformation(val uuid: String, val bank: String, val number: String, val balance: Double)

@Serializable
data class AccountInfo(
    @SerialName("id")
    val uuid: String,
    @SerialName("account_name")
    val bank: String,
    @SerialName("account_number")
    val number: String,
    @SerialName("account_balance")
    val balance: Double
)

@Serializable
data class AccountResponseData(
    @SerialName("data")
    val body: AccountInfo
)

@Serializable
data class HistoryDetail(
    @SerialName("amount")
    val amount: Double,
    @SerialName("balance")
    val changes: Double,
    @SerialName("history_type")
    val transactionType: String,
    @SerialName("sender")
    val sender: String,
    @SerialName("receiver")
    val receiver: String,
    @SerialName("created_at")
    val time: List<Int>
)

@Serializable
data class AccountHistoryResponseData(
    @SerialName("data")
    val body: List<HistoryDetail>
)