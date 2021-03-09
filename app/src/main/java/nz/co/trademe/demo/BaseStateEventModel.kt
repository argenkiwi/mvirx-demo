package nz.co.trademe.demo

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect

abstract class BaseStateEventModel<S, E>(
    initialState: S
) : BaseEventModel<E>() {

    private val _state = MutableStateFlow(initialState)
    val state: StateFlow<S> = _state

    suspend fun launch() {
        events.collect {
            with(_state) {
                value = reduce(value, it)
            }
        }
    }

    abstract fun reduce(state: S, event: E): S
}
