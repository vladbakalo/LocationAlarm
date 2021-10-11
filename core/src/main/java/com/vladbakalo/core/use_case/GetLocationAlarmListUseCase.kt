package com.vladbakalo.core.use_case

import com.vladbakalo.core.base.SuspendUseCase
import com.vladbakalo.core.db.models.LocationAlarmDb
import com.vladbakalo.core.db.repo.LocationAlarmRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow

class GetLocationAlarmListUseCase (private val locationAlarmRepository: LocationAlarmRepository,
                                                      private val defaultDispatcher: CoroutineDispatcher) :
    SuspendUseCase<Unit?, Flow<List<LocationAlarmDb>>>(defaultDispatcher) {

    override suspend fun execute(parameters: Unit?): Flow<List<LocationAlarmDb>> {
        return locationAlarmRepository.getAllLocationAlarm()
    }
}