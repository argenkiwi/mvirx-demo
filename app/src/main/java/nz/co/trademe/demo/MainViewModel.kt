package nz.co.trademe.demo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    private val model = MainModel(MainState(10));

    val liveEvents = model.events.asLiveData()
    val liveState = model.state.asLiveData()

    init {
        viewModelScope.launch { model.launch() }

        viewModelScope.launch {
            while (true) {
                delay(1000)
                model.publish(MainEvent.Decrement)
            }
        }
    }

    fun resume() {
        viewModelScope.launch { model.publish(MainEvent.Resume) }
    }

    fun pause() {
        viewModelScope.launch { model.publish(MainEvent.Pause) }
    }

    fun increment() {
        viewModelScope.launch { model.publish(MainEvent.Increment) }
    }
}
