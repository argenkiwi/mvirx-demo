package nz.co.trademe.demo

import androidx.lifecycle.LiveData
import androidx.lifecycle.LiveDataReactiveStreams
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {

    private val model = MainModel()

    val liveState: LiveData<MainState> = LiveDataReactiveStreams.fromPublisher(model.state)

    private val disposable = model.subscribe()

    override fun onCleared() {
        super.onCleared()
        disposable.dispose()
    }

    fun increment() {
        model.events.onNext(MainEvent.Increment)
    }
}
