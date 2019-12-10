package nz.co.trademe.demo

import androidx.lifecycle.LiveData
import androidx.lifecycle.LiveDataReactiveStreams
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {

    private val model = MainModel()

    val liveEvents: LiveData<MainEvent>
        get() = LiveDataReactiveStreams.fromPublisher(model.events)
    val liveState: LiveData<MainState> = LiveDataReactiveStreams.fromPublisher(model.state)

    private val disposable = model.subscribe()

    override fun onCleared() {
        super.onCleared()
        disposable.dispose()
    }

    fun increment() {
        model.events.onNext(MainEvent.Increment)
    }

    fun resume() {
        model.events.onNext(MainEvent.Resume)
    }

    fun pause() {
        model.events.onNext(MainEvent.Pause)
    }
}
