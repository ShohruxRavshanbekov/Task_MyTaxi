package uz.futuresoft.mytaxi_task.presentation.mainActivity

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import uz.futuresoft.mytaxi_task.domain.useCase.GetLocationUseCase
import uz.futuresoft.mytaxi_task.presentation.mainActivity.event.GetLocationEvent
import uz.futuresoft.mytaxi_task.presentation.mainActivity.event.MainActivityEvent
import uz.futuresoft.mytaxi_task.presentation.mainActivity.state.MainActivityState
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val getLocationUseCase: GetLocationUseCase,
) : ViewModel() {

    private val _state = mutableStateOf(MainActivityState())
    val state: State<MainActivityState> = _state

    fun send(event: MainActivityEvent) {
        when (event) {
            is GetLocationEvent -> getLocation()
        }
    }

    private fun getLocation() {
        viewModelScope.launch {
            _state.value = MainActivityState(location = getLocationUseCase.invoke())
        }
    }
}