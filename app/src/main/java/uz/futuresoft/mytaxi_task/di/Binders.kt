package uz.futuresoft.mytaxi_task.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import uz.futuresoft.mytaxi_task.data.repository.LocationRepositoryImpl
import uz.futuresoft.mytaxi_task.data.useCase.GetLocationUseCaseImpl
import uz.futuresoft.mytaxi_task.data.useCase.InsertLocationUseCaseImpl
import uz.futuresoft.mytaxi_task.domain.repository.LocationRepository
import uz.futuresoft.mytaxi_task.domain.useCase.GetLocationUseCase
import uz.futuresoft.mytaxi_task.domain.useCase.InsertLocationUseCase

@Module
@InstallIn(SingletonComponent::class)
interface Binders {

    @Binds
    fun bindLocationRepository(locationRepositoryImpl: LocationRepositoryImpl): LocationRepository

    @Binds
    fun bindInsertLocationUseCase(insertLocationUseCaseImpl: InsertLocationUseCaseImpl): InsertLocationUseCase

    @Binds
    fun bindGetLocationUseCase(getLocationUseCaseImpl: GetLocationUseCaseImpl): GetLocationUseCase
}