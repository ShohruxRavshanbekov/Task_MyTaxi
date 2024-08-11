package uz.futuresoft.mytaxi_task.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import uz.futuresoft.mytaxi_task.data.dataSource.local.database.LocationDatabase
import uz.futuresoft.mytaxi_task.data.dataSource.local.database.dao.LocationDao
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): LocationDatabase {
        return Room.databaseBuilder(
            context = context,
            klass = LocationDatabase::class.java,
            name = LocationDatabase.DATABASE_NAME
        ).build()
    }

    @Provides
    fun provideUserDao(database: LocationDatabase): LocationDao {
        return database.locationDao()
    }
}