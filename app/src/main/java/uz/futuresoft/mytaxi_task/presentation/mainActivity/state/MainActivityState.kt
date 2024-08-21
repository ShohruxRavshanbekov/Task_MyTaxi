package uz.futuresoft.mytaxi_task.presentation.mainActivity.state

import uz.futuresoft.mytaxi_task.domain.model.Location

data class MainActivityState(
    val location: Location = Location(latitude = 0.0, longitude = 0.0, bearing = 0.0),
)
