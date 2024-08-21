package uz.futuresoft.mytaxi_task.domain.repository

import kotlinx.coroutines.flow.Flow
import uz.futuresoft.mytaxi_task.domain.model.Location

interface LocationRepository {
    suspend fun insertLocation(location: Location)
    suspend fun getLocation(): Flow<Location>
}