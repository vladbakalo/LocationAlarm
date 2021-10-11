package com.vladbakalo.core.db.repo

import android.content.Context
import com.vladbakalo.core.db.base.BaseRepository
import com.vladbakalo.core.db.dao.LocationAlarmDao
import com.vladbakalo.core.db.models.LocationAlarmDb
import com.vladbakalo.core.db.models.LocationAlarmWithAlarmDistancesDb
import kotlinx.coroutines.flow.Flow


open class LocationAlarmRepository(private val locationAlarmDao: LocationAlarmDao,
                                   val context: Context) :BaseRepository() {

    fun getAllLocationAlarm(): Flow<List<LocationAlarmDb>> {
        return locationAlarmDao.getAllSorted()
    }

    suspend fun getAllLocationAlarmsWithAlarms(): List<LocationAlarmWithAlarmDistancesDb> {
        return locationAlarmDao.getAllLocationAlarmWithAlarms()
    }

    fun getAllEnabledLocationAlarmsWithAlarms(): Flow<List<LocationAlarmWithAlarmDistancesDb>> {
        return locationAlarmDao.getAllEnabledLocationAlarmWithAlarms()
    }

    suspend fun deleteLocationAlarmById(id: Long) {
        locationAlarmDao.delete(id)
    }

    suspend fun getLocationAlarmById(id: Long): LocationAlarmDb {
        return locationAlarmDao.getEntityById(id)
    }

    suspend fun getLocationAlarmWithAlarmDistancesById(id: Long): LocationAlarmWithAlarmDistancesDb {
        return locationAlarmDao.getLocationAlarmsWithAlarmsById(id)
    }

    suspend fun isExists(name: String): Boolean {
        return locationAlarmDao.getEntityByName(name) != null
    }

    suspend fun createOrUpdate(model: LocationAlarmDb) {
        locationAlarmDao.insert(model)
    }

    suspend fun update(model: LocationAlarmDb) {
        locationAlarmDao.update(model)
    }
}