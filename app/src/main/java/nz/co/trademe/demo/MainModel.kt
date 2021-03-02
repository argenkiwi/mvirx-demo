package nz.co.trademe.demo

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlin.math.max

sealed class MainEvent {
    object Increment : MainEvent()
    object Decrement : MainEvent()
    object Pause : MainEvent()
    object Resume : MainEvent()
}

data class MainState(val time: Int, val paused: Boolean = false) {

    fun reduce(event: MainEvent) = when (event) {
        is MainEvent.Increment -> copy(time = time + 1)
        is MainEvent.Decrement -> when {
            paused -> this
            else -> copy(time = max(0, time - 1))
        }
        is MainEvent.Pause -> copy(paused = true)
        is MainEvent.Resume -> copy(paused = false)
    }
}

class MainModel {
    val events = MutableSharedFlow<MainEvent>()
    val state = MutableStateFlow(MainState(10))
}
