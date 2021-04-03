package nz.co.trademe.demo

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.collect

suspend fun <S, E> MutableStateFlow<S>.scan(
    events: SharedFlow<E>,
    reduce: (S, E) -> S
) {
    events.collect { emit(reduce(value, it)) }
}
