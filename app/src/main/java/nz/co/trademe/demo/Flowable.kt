package nz.co.trademe.demo

import io.reactivex.rxjava3.core.Flowable

fun <T, S> Flowable<T>.mapNotNull(mapper: (T) -> S): Flowable<S> = map { Optional(mapper(it)) }
    .filter { it.value != null }
    .map { it.value }

fun <T, S, R> Flowable<T>.withLatestFromNotNull(
    source: Flowable<S>,
    mapper: (T, S) -> R?
): Flowable<R> = withLatestFrom(source) { t, s -> Optional(mapper(t, s)) }
    .filter { it.value != null }
    .map { it.value }

data class Optional<T>(val value: T?)
