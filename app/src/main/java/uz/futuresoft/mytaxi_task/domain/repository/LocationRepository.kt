package uz.futuresoft.mytaxi_task.domain.repository

import uz.futuresoft.mytaxi_task.domain.model.Location

interface LocationRepository {
    suspend fun insertLocation(location: Location)
    suspend fun getLocation(): Location?
}