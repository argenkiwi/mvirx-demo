package nz.co.trademe.demo

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

abstract class BaseViewModel<S : Reducible<S, E>, E>(
    initialState: S
) : ViewModel() {

    private val events = MutableSharedFlow<E>()
    val liveEvents: LiveData<E> = events.asLiveData()

    private val state = MutableStateFlow(initialState)
    val liveState: LiveData<S> = state.asLiveData()

    init {
        viewModelScope.launch {
            events.collect {
                state.value = state.value.reduce(it)
            }
        }
    }

    fun publish(event: E) {
        viewModelScope.launch {
            events.emit(event)
        }
    }
}
