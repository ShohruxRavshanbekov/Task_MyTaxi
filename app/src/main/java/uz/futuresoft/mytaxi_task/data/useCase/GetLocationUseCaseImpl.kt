package uz.futuresoft.mytaxi_task.data.useCase

import uz.futuresoft.mytaxi_task.domain.model.Location
import uz.futuresoft.mytaxi_task.domain.repository.LocationRepository
import uz.futuresoft.mytaxi_task.domain.useCase.GetLocationUseCase
import javax.inject.Inject

class GetLocationUseCaseImpl @Inject constructor(
    private val locationRepository: LocationRepository,
) : GetLocationUseCase {

    override suspend fun invoke(): Location? = locationRepository.getLocation()
}