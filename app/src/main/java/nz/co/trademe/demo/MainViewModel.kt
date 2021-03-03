package nz.co.trademe.demo

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    private val events = MutableSharedFlow<MainEvent>()
    val liveEvents: LiveData<MainEvent> = events.asLiveData()

    private val state = MutableStateFlow(MainState(10))
    val liveState: LiveData<MainState> = state.asLiveData()

    init {

        viewModelScope.launch {
            events.collect {
                state.value = state.value.reduce(it)
            }
        }

        viewModelScope.launch {
            while (true) {
                delay(1000)
                events.emit(MainEvent.Decrement)
            }
        }
    }

    fun increment() {
        viewModelScope.launch {
            events.emit(MainEvent.Increment)
        }
    }

    fun resume() {
        viewModelScope.launch {
            events.emit(MainEvent.Resume)
        }
    }

    fun pause() {
        viewModelScope.launch {
            events.emit(MainEvent.Pause)
        }
    }
}
