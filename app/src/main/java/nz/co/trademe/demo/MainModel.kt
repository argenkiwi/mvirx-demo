package nz.co.trademe.demo

import kotlin.math.max

object MainModel {

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

    class Reactor {

        fun react(event: Event): Event? = when (event) {
            Event.Decrement -> null
            Event.Increment -> null
            Event.Pause -> null
            Event.Resume -> null
        }
    }

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
}
