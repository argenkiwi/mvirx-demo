package nz.co.trademe.demo

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.launch

suspend fun <E> MutableSharedFlow<E>.react(scope: CoroutineScope, react: suspend (E) -> E?) {
    mapNotNull(react).collect { scope.launch { emit(it) } }
}
