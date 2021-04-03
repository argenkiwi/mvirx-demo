package nz.co.trademe.demo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    private val events = MutableSharedFlow<MainModel.Event>()
    val liveEvents = events.asLiveData()

    private val state = MutableStateFlow(MainModel.State(10))
    val liveState = state.asLiveData()

    init {

        viewModelScope.launch {

            launch {
                events.scan(MainModel.State(10), MainModel::reduce)
                    .stateIn(viewModelScope)
                    .collect { state.value = it }
            }

            launch {
                while (true) {
                    delay(1000)
                    events.emit(MainModel.Event.Decrement)
                }
            }
        }
    }

    fun resume() {
        viewModelScope.launch { events.emit(MainModel.Event.Resume) }
    }

    fun pause() {
        viewModelScope.launch { events.emit(MainModel.Event.Pause) }
    }

    fun increment() {
        viewModelScope.launch { events.emit(MainModel.Event.Increment) }
    }
}
