package nz.co.trademe.demo

import kotlin.math.max

object MainModel {

    fun reduce(state: State, event: Event) = with(state) {

        when (event) {
            is Event.Increment -> copy(time = time + 1)
            is Event.Decrement -> when {
                paused -> this
                else -> copy(time = max(0, time - 1))
            }
            is Event.Pause -> copy(paused = true)
            is Event.Resume -> copy(paused = false)
        }
    }

    sealed class Event {
        object Increment : Event()
        object Decrement : Event()
        object Pause : Event()
        object Resume : Event()
    }

    data class State(
        val time: Int,
        val paused: Boolean = false
    )
}
