package news

import asset.subMenu.MyStockAPI
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import moe.tlaster.precompose.viewmodel.ViewModel
import moe.tlaster.precompose.viewmodel.viewModelScope

class NewsViewModel(): ViewModel() {
    private val _newses: MutableStateFlow<List<NewsDetail>> = MutableStateFlow(listOf())

    val newses: StateFlow<List<NewsDetail>> = _newses

    private val _isLoading: MutableStateFlow<Boolean> = MutableStateFlow(true)

    val isLoading: StateFlow<Boolean> = _isLoading

    fun fetchData(brand: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _newses.emit(NewsAPI().getNewses(brand))
            _isLoading.emit(false)
        }
    }
}