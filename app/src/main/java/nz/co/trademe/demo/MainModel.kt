package nz.co.trademe.demo

import kotlin.math.max

sealed class MainEvent {
    object Increment : MainEvent()
    object Decrement : MainEvent()
}

data class MainState(val time: Int) {
    fun reduce(event: MainEvent) = when (event) {
        is MainEvent.Increment -> copy(time = time + 1)
        is MainEvent.Decrement -> copy(time = max(0, time - 1))
    }
}
