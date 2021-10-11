package com.vladbakalo.map.use_case

import com.vladbakalo.core.db.repo.LocationAlarmRepository
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineDispatcher

@Module
class UseCaseModule {

    @Provides
    fun provideChangeLocationAlarmPositionUseCase(locationAlarmRepository: LocationAlarmRepository, coroutineDispatcher: CoroutineDispatcher): ChangeLocationAlarmPositionUseCase{
        return ChangeLocationAlarmPositionUseCase(locationAlarmRepository, coroutineDispatcher)
    }
}