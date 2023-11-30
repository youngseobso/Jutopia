package asset

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import moe.tlaster.precompose.stateholder.SavedStateHolder
import moe.tlaster.precompose.viewmodel.ViewModel

class AssetViewModel(savedStateHolder: SavedStateHolder): ViewModel() {
    private val _chipIdx = mutableStateOf(savedStateHolder.consumeRestored("chipIdx") as Int? ?: 0)

    val chipIdx: State<Int> = _chipIdx

    init {
        savedStateHolder.registerProvider("chipIdx") {
            _chipIdx.value
        }
    }

    fun setChipIdx(idx: Int) {
        _chipIdx.value = idx
    }
}