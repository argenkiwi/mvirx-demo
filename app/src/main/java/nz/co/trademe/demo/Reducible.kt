package nz.co.trademe.demo

interface Reducible<S, E> {
    fun reduce(event: E): S
}
