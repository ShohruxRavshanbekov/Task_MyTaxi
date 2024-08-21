package uz.futuresoft.mytaxi_task.domain.useCase

import kotlinx.coroutines.flow.Flow
import uz.futuresoft.mytaxi_task.domain.model.Location

interface GetLocationUseCase {
    suspend fun invoke(): Flow<Location>
}