package uz.futuresoft.mytaxi_task.data.repository

import uz.futuresoft.mytaxi_task.data.dataSource.local.database.dao.LocationDao
import uz.futuresoft.mytaxi_task.domain.model.Location
import uz.futuresoft.mytaxi_task.domain.repository.LocationRepository
import javax.inject.Inject

class LocationRepositoryImpl @Inject constructor(
    private val locationDao: LocationDao,
) : LocationRepository {
    override suspend fun insertLocation(location: Location) {
        locationDao.insertLocation(location = location)
    }

    override suspend fun getLocation(): Location? = locationDao.getLocation()
}