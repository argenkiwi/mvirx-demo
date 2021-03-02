package nz.co.trademe.demo

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    private val model = MainModel()

    val liveEvents: LiveData<MainEvent>
        get() = model.events.asLiveData()

    val liveState: LiveData<MainState> = model.state.asLiveData()

    init {
        viewModelScope.launch {
            with(model) {
                events.collect {
                    state.value = state.value.reduce(it)
                }
            }
        }

        viewModelScope.launch {
            with(model) {
                while(true) {
                    delay(1000)
                    events.emit(MainEvent.Decrement)
                }
            }
        }
    }

    fun increment() {
        viewModelScope.launch {
            model.events.emit(MainEvent.Increment)
        }
    }

    fun resume() {
        viewModelScope.launch {
            model.events.emit(MainEvent.Resume)
        }
    }

    fun pause() {
        viewModelScope.launch {
            model.events.emit(MainEvent.Pause)
        }
    }
}
