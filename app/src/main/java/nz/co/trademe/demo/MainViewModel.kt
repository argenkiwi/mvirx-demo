package nz.co.trademe.demo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.toLiveData
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.processors.BehaviorProcessor
import io.reactivex.rxjava3.processors.PublishProcessor
import java.util.concurrent.TimeUnit

class MainViewModel : ViewModel() {

    private val events = PublishProcessor.create<MainModel.Event>()
    val liveEvents get() = events.toLiveData()

    private val state = BehaviorProcessor.create<MainModel.State>()
    val liveState = state.toLiveData()

    private val reactor = MainModel.Reactor()

    private val disposable = CompositeDisposable(
        events.mapNotNull(reactor::react).subscribe(events::onNext),
        events
            .scan(MainModel.State(10), MainModel::reduce)
            .subscribe { state.onNext(it) },
        Flowable
            .timer(1000, TimeUnit.MILLISECONDS)
            .repeat()
            .subscribe { events.onNext(MainModel.Event.Decrement) }
    )

    override fun onCleared() {
        super.onCleared()
        disposable.dispose()
    }

    fun increment() {
        events.onNext(MainModel.Event.Increment)
    }

    fun resume() {
        events.onNext(MainModel.Event.Resume)
    }

    fun pause() {
        events.onNext(MainModel.Event.Pause)
    }
}
