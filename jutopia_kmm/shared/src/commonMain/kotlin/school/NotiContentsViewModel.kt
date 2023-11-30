package school

import androidx.compose.runtime.State
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import moe.tlaster.precompose.viewmodel.ViewModel
import moe.tlaster.precompose.viewmodel.viewModelScope

class NotiContentsViewModel(private val idx: Int): ViewModel() {
    private val _notice: MutableStateFlow<NoticeDetail?> = MutableStateFlow(null)

    val notice: StateFlow<NoticeDetail?> = _notice

    private val _isLoading: MutableStateFlow<Boolean> = MutableStateFlow(true)

    val isLoading: StateFlow<Boolean> = _isLoading

    fun fetchDetailData() {
        viewModelScope.launch(Dispatchers.IO) {
            _notice.emit(SchoolAPI().getNotiDetail(idx))
            _isLoading.emit(false)
        }
    }
}