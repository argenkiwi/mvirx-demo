package nz.co.trademe.demo

import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.processors.FlowableProcessor
import io.reactivex.rxjava3.processors.PublishProcessor

abstract class BaseEventModel<E> {

    private val _events: FlowableProcessor<E> = PublishProcessor.create()
    val events: Flowable<E> = _events

    fun publish(event: E) {
        _events.onNext(event)
    }
}
