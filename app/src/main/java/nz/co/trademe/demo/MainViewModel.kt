package nz.co.trademe.demo

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainViewModel : BaseViewModel<MainState, MainEvent>(MainState(10)) {

    init {
        viewModelScope.launch {
            while (true) {
                delay(1000)
                publish(MainEvent.Decrement)
            }
        }
    }
}
