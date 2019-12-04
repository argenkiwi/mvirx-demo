package nz.co.trademe.demo

import androidx.lifecycle.LiveData
import androidx.lifecycle.LiveDataReactiveStreams
import androidx.lifecycle.ViewModel
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.processors.BehaviorProcessor
import io.reactivex.processors.FlowableProcessor
import io.reactivex.processors.PublishProcessor
import java.util.concurrent.TimeUnit

class MainViewModel : ViewModel() {

    private val events: FlowableProcessor<MainEvent> = PublishProcessor.create()
    private val state: FlowableProcessor<MainState> = BehaviorProcessor.create()

    val liveState: LiveData<MainState> = LiveDataReactiveStreams.fromPublisher(state)

    private val disposable = CompositeDisposable(
        events
            .scan(MainState(10), { state, event -> state.reduce(event) })
            .subscribe { state.onNext(it) },
        Observable
            .timer(1000, TimeUnit.MILLISECONDS)
            .repeat()
            .subscribe { decrement() }
    )

    override fun onCleared() {
        super.onCleared()
        disposable.dispose()
    }

    fun increment() {
        events.onNext(MainEvent.Increment)
    }

    private fun decrement() {
        events.onNext(MainEvent.Decrement)
    }
}
