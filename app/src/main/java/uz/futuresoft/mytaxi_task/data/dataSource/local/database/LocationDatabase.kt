package uz.futuresoft.mytaxi_task.data.dataSource.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import uz.futuresoft.mytaxi_task.data.dataSource.local.database.dao.LocationDao
import uz.futuresoft.mytaxi_task.domain.model.Location

@Database(entities = [Location::class], version = 1)
abstract class LocationDatabase : RoomDatabase() {
    abstract fun locationDao(): LocationDao

    companion object {
        const val DATABASE_NAME = "location_database"
    }
}