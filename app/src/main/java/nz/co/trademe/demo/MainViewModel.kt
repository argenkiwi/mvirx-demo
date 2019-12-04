package nz.co.trademe.demo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    private val _liveState = MutableLiveData<MainState>()
    val liveState: LiveData<MainState>
        get() = _liveState

    init {
        _liveState.value = MainState(10)

        viewModelScope.launch {
            while (true) {
                delay(1000)
                decrement()
            }
        }
    }

    fun increment() {
        _liveState.value?.let {
            _liveState.value = it.reduce(MainEvent.Increment)
        }
    }

    private fun decrement() {
        _liveState.value?.let {
            _liveState.value = it.reduce(MainEvent.Decrement)
        }
    }
}
