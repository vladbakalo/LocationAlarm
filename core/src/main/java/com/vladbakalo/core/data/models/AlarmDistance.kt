package com.vladbakalo.core.data.models

import com.vladbakalo.core.db.models.AlarmDistanceDb

class AlarmDistance() {
    var id: Long = 0
    var locationAlarmId: Long = 0
    var notifyDistanceMeters = ""
    var enabled = true

    constructor(model: AlarmDistanceDb) :this() {
        id = model.id
        locationAlarmId = model.locationAlarmId
        notifyDistanceMeters = model.notifyDistanceMeters.toString()
        enabled = model.enabled
    }

    fun toDbInstance(): AlarmDistanceDb {
        return AlarmDistanceDb(id, enabled, locationAlarmId, notifyDistanceMeters.toInt())
    }

    override fun equals(other: Any?): Boolean {
        if (other != null && other is AlarmDistance) {
            return this.notifyDistanceMeters == other.notifyDistanceMeters
        }
        return super.equals(other)
    }

    override fun hashCode(): Int {
        return notifyDistanceMeters.hashCode()
    }
}