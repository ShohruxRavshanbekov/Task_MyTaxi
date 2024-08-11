package uz.futuresoft.mytaxi_task.data.useCase

import uz.futuresoft.mytaxi_task.domain.model.Location
import uz.futuresoft.mytaxi_task.domain.repository.LocationRepository
import uz.futuresoft.mytaxi_task.domain.useCase.GetLocationUseCase
import uz.futuresoft.mytaxi_task.domain.useCase.InsertLocationUseCase
import javax.inject.Inject

class InsertLocationUseCaseImpl @Inject constructor(
    private val locationRepository: LocationRepository,
) : InsertLocationUseCase {

    override suspend fun invoke(location: Location) {
        locationRepository.insertLocation(location = location)
    }
}