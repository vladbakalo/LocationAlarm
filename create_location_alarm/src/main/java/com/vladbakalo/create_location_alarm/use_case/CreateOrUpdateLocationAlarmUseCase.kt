package com.vladbakalo.create_location_alarm.use_case

import com.vladbakalo.core.base.SuspendUseCase
import com.vladbakalo.core.data.models.AlarmDistance
import com.vladbakalo.core.data.models.LocationAlarm
import com.vladbakalo.core.db.repo.AlarmDistanceRepository
import com.vladbakalo.core.db.repo.LocationAlarmRepository
import kotlinx.coroutines.CoroutineDispatcher

class CreateOrUpdateLocationAlarmUseCase constructor(private val locationAlarmRepository: LocationAlarmRepository,
                                                     private val alarmDistanceRepository: AlarmDistanceRepository,
                                                     private val defaultDispatcher: CoroutineDispatcher) :
    SuspendUseCase<Pair<LocationAlarm, List<AlarmDistance>>, Unit>(defaultDispatcher) {

    override suspend fun execute(parameters: Pair<LocationAlarm, List<AlarmDistance>>) {
        val locationAlarm = parameters.first.toDbInstance()
        val alarmDistanceList = parameters.second.map { it.toDbInstance() }

        locationAlarmRepository.createOrUpdate(locationAlarm)
        alarmDistanceRepository.createOrUpdateAlarmsById(alarmDistanceList, locationAlarm.id)
    }
}