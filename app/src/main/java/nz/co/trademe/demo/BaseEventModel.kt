package nz.co.trademe.demo

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow

abstract class BaseEventModel<E> {

    private val _events = MutableSharedFlow<E>()
    val events: SharedFlow<E> = _events

    suspend fun publish(event: E) {
        _events.emit(event)
    }
}
