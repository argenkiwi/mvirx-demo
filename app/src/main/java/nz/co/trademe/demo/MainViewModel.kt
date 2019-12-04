package nz.co.trademe.demo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.max

class MainViewModel : ViewModel() {

    private val _liveState = MutableLiveData<Int>()
    val liveState: LiveData<Int>
        get() = _liveState

    init {
        _liveState.value = 10

        viewModelScope.launch {
            while (true) {
                delay(1000)
                decrement()
            }
        }
    }

    fun increment() {
        _liveState.value?.let {
            _liveState.value = it + 1
        }
    }

    private fun decrement() {
        _liveState.value?.let {
            _liveState.value = max(0, it - 1)
        }
    }
}
