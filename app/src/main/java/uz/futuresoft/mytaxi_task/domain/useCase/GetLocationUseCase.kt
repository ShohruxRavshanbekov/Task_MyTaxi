package uz.futuresoft.mytaxi_task.domain.useCase

import uz.futuresoft.mytaxi_task.domain.model.Location

interface GetLocationUseCase {
    suspend fun invoke(): Location?
}