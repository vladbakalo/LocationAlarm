package com.vladbakalo.core.use_case

import com.vladbakalo.core.base.SuspendUseCase
import com.vladbakalo.core.db.repo.AlarmDistanceRepository
import com.vladbakalo.core.db.repo.LocationAlarmRepository
import kotlinx.coroutines.CoroutineDispatcher

class DeleteLocationAlarmUserCase (private val locationAlarmRepository: LocationAlarmRepository,
                                                      private val alarmDistanceDistanceRepository: AlarmDistanceRepository,
                                                      private val defaultDispatcher: CoroutineDispatcher) :
    SuspendUseCase<Long, Unit>(defaultDispatcher) {

    override suspend fun execute(parameters: Long) {
        locationAlarmRepository.deleteLocationAlarmById(parameters)
        alarmDistanceDistanceRepository.deleteAlarmDistancesByLocationAlarmId(parameters)
    }
}