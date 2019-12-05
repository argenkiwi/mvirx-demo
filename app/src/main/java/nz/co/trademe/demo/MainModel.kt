package nz.co.trademe.demo

import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.processors.BehaviorProcessor
import io.reactivex.processors.FlowableProcessor
import io.reactivex.processors.PublishProcessor
import java.util.concurrent.TimeUnit
import kotlin.math.max

sealed class MainEvent {
    object Increment : MainEvent()
    object Decrement : MainEvent()
    object Pause : MainEvent()
    object Resume : MainEvent()
}

data class MainState(val time: Int, val paused: Boolean = false) {
    fun reduce(event: MainEvent) = when (event) {
        is MainEvent.Increment -> copy(time = time + 1)
        is MainEvent.Decrement -> when {
            paused -> this
            else -> copy(time = max(0, time - 1))
        }
        is MainEvent.Pause -> copy(paused = true)
        is MainEvent.Resume -> copy(paused = false)
    }
}

class MainModel {

    val events: FlowableProcessor<MainEvent> = PublishProcessor.create()
    val state: FlowableProcessor<MainState> = BehaviorProcessor.create()

    fun subscribe() = CompositeDisposable(
        events
            .scan(MainState(10), { state, event -> state.reduce(event) })
            .subscribe { state.onNext(it) },
        Observable
            .timer(1000, TimeUnit.MILLISECONDS)
            .repeat()
            .subscribe { events.onNext(MainEvent.Decrement) }
    )
}
