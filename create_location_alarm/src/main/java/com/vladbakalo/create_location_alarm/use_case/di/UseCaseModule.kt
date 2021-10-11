package com.vladbakalo.create_location_alarm.use_case.di

import android.content.Context
import com.vladbakalo.core.db.repo.AlarmDistanceRepository
import com.vladbakalo.core.db.repo.LocationAlarmRepository
import com.vladbakalo.create_location_alarm.use_case.CreateOrUpdateLocationAlarmUseCase
import com.vladbakalo.create_location_alarm.use_case.GetAddressNameByLocationUseCase
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineDispatcher

@Module
class UseCaseModule {

    @Provides
    fun provideCreateOrUpdateLocationAlarmUseCase(locationAlarmRepository: LocationAlarmRepository,
                                                  alarmDistanceRepository: AlarmDistanceRepository,
                                                  coroutineDispatcher: CoroutineDispatcher): CreateOrUpdateLocationAlarmUseCase{
        return CreateOrUpdateLocationAlarmUseCase(locationAlarmRepository, alarmDistanceRepository, coroutineDispatcher)
    }

    @Provides
    fun provideGetAddressNameByLocationUseCase(context: Context, coroutineDispatcher: CoroutineDispatcher): GetAddressNameByLocationUseCase{
        return GetAddressNameByLocationUseCase(context, coroutineDispatcher)
    }
}