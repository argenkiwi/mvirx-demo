package nz.co.trademe.demo

import androidx.lifecycle.LiveData
import androidx.lifecycle.LiveDataReactiveStreams
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.reactivex.processors.BehaviorProcessor
import io.reactivex.processors.FlowableProcessor
import io.reactivex.processors.PublishProcessor
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    private val events: FlowableProcessor<MainEvent> = PublishProcessor.create()
    private val state: FlowableProcessor<MainState> = BehaviorProcessor.create()

    val liveState: LiveData<MainState> = LiveDataReactiveStreams.fromPublisher(state)

    private val disposable = events
        .scan(MainState(10), { state, event -> state.reduce(event) })
        .subscribe { state.onNext(it) }

    init {
        viewModelScope.launch {
            while (true) {
                delay(1000)
                decrement()
            }
        }
    }

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
