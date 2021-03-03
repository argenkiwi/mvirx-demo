package nz.co.trademe.demo

import kotlin.math.max

sealed class MainEvent {
    object Increment : MainEvent()
    object Decrement : MainEvent()
    object Pause : MainEvent()
    object Resume : MainEvent()
}

data class MainState(val time: Int, val paused: Boolean = false) : Reducible<MainState, MainEvent> {

    override fun reduce(event: MainEvent) = when (event) {
        is MainEvent.Increment -> copy(time = time + 1)
        is MainEvent.Decrement -> when {
            paused -> this
            else -> copy(time = max(0, time - 1))
        }
        is MainEvent.Pause -> copy(paused = true)
        is MainEvent.Resume -> copy(paused = false)
    }
}
