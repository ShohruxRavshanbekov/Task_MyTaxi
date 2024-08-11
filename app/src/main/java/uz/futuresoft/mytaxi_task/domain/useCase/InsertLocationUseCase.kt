package uz.futuresoft.mytaxi_task.domain.useCase

import uz.futuresoft.mytaxi_task.domain.model.Location

interface InsertLocationUseCase {
    suspend fun invoke(location: Location)
}