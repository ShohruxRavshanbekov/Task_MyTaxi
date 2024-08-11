package uz.futuresoft.mytaxi_task.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "location")
data class Location(
    @PrimaryKey
    val id: Int = 0,
    val latitude: Double,
    val longitude: Double,
)
