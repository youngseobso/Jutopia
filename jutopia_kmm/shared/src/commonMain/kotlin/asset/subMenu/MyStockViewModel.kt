package asset.subMenu

import androidx.compose.runtime.State
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import moe.tlaster.precompose.viewmodel.ViewModel
import moe.tlaster.precompose.viewmodel.viewModelScope

class MyStockViewModel: ViewModel() {
    private val _ownedStock: MutableStateFlow<List<StockDetail>> = MutableStateFlow(listOf())

    val ownedStock: StateFlow<List<StockDetail>> = _ownedStock

    private val _isLoading: MutableStateFlow<Boolean> = MutableStateFlow(true)

    val isLoading: StateFlow<Boolean> = _isLoading

    fun fetchData(userUUID: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _ownedStock.emit(MyStockAPI().getMyStock(userUUID))
            _isLoading.emit(false)
        }
    }
}