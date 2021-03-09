package nz.co.trademe.demo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.toLiveData
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import java.util.concurrent.TimeUnit

class MainViewModel : ViewModel() {

    private val model = MainModel(MainState(10))

    val liveEvents get() = model.events.toLiveData()
    val liveState = model.state.toLiveData()

    private val disposable = CompositeDisposable(
        model.launch(),
        Flowable
            .timer(1000, TimeUnit.MILLISECONDS)
            .repeat()
            .subscribe { model.publish(MainEvent.Decrement) }
    )

    override fun onCleared() {
        super.onCleared()
        disposable.dispose()
    }

    fun increment() {
        model.publish(MainEvent.Increment)
    }

    fun resume() {
        model.publish(MainEvent.Resume)
    }

    fun pause() {
        model.publish(MainEvent.Pause)
    }
}
