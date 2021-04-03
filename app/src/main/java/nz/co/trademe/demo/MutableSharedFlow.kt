package nz.co.trademe.demo

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

suspend fun <E> MutableSharedFlow<E>.sideEffects(
    scope: CoroutineScope,
    sideEffect: suspend (E) -> E
) {
    map(sideEffect)
        .distinctUntilChanged()
        .collect { scope.launch { emit(it) } }
}
