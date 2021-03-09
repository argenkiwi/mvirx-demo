package nz.co.trademe.demo

import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.processors.BehaviorProcessor
import io.reactivex.rxjava3.processors.FlowableProcessor

abstract class BaseStateEventModel<S, E>(
    private val initialState: S
) : BaseEventModel<E>() {

    private val _state: FlowableProcessor<S> = BehaviorProcessor.create()
    val state: Flowable<S> = _state

    fun launch(): Disposable = events
        .scan(initialState, ::reduce)
        .subscribe { _state.onNext(it) }

    abstract fun reduce(state: S, event: E): S
}
