package com.vladbakalo.core.use_case.di

import com.vladbakalo.core.db.repo.AlarmDistanceRepository
import com.vladbakalo.core.db.repo.LocationAlarmRepository
import com.vladbakalo.core.use_case.*
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineDispatcher

@Module
class UseCaseModule {

    @Provides
    fun provideChangeLocationAlarmEnabledStateUseCase(locationAlarmRepository: LocationAlarmRepository,
                                                      dispatcher: CoroutineDispatcher): ChangeLocationAlarmEnabledStateUseCase {
        return ChangeLocationAlarmEnabledStateUseCase(locationAlarmRepository, dispatcher)
    }

    @Provides
    fun provideDeleteLocationAlarmUserCase(locationAlarmRepository: LocationAlarmRepository,
                                           alarmDistanceRepository: AlarmDistanceRepository,
                                           dispatcher: CoroutineDispatcher): DeleteLocationAlarmUserCase {
        return DeleteLocationAlarmUserCase(locationAlarmRepository, alarmDistanceRepository,
            dispatcher)
    }

    @Provides
    fun provideGetLocationAlarmListUseCase(locationAlarmRepository: LocationAlarmRepository,
                                           dispatcher: CoroutineDispatcher): GetLocationAlarmListUseCase {
        return GetLocationAlarmListUseCase(locationAlarmRepository, dispatcher)
    }

    @Provides
    fun provideGetLocationAlarmWithAlarmDistancesListUseCase(locationAlarmRepository: LocationAlarmRepository,
                                                             dispatcher: CoroutineDispatcher): GetLocationAlarmWithAlarmDistancesListUseCase {
        return GetLocationAlarmWithAlarmDistancesListUseCase(locationAlarmRepository, dispatcher)
    }

    @Provides
    fun provideGetLocationAlarmWithAlarmDistancesUseCase(locationAlarmRepository: LocationAlarmRepository,
                                                         dispatcher: CoroutineDispatcher): GetLocationAlarmWithAlarmDistancesUseCase {
        return GetLocationAlarmWithAlarmDistancesUseCase(locationAlarmRepository, dispatcher)
    }
}