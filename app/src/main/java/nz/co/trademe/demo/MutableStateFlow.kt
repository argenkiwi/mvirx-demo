package nz.co.trademe.demo

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map

suspend fun <S, E> MutableStateFlow<S>.reduce(events: SharedFlow<E>, reduce: (S, E) -> S) {
    events.map { reduce(value, it) }.collect(::emit)
}
