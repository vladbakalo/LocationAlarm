package com.vladbakalo.core.use_case

import com.vladbakalo.core.base.SuspendUseCase
import com.vladbakalo.core.db.repo.LocationAlarmRepository
import kotlinx.coroutines.CoroutineDispatcher

class ChangeLocationAlarmEnabledStateUseCase (private val locationAlarmRepository: LocationAlarmRepository,
                                                         private val defaultDispatcher: CoroutineDispatcher) :
    SuspendUseCase<Pair<Long, Boolean>, Unit>(defaultDispatcher) {

    override suspend fun execute(parameters: Pair<Long, Boolean>) {
        val locationAlarm = locationAlarmRepository.getLocationAlarmById(parameters.first)
        locationAlarm.enabled = parameters.second

        locationAlarmRepository.update(locationAlarm)
    }
}