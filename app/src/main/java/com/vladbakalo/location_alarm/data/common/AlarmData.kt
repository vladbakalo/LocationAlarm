package com.vladbakalo.location_alarm.data.common

import androidx.lifecycle.MutableLiveData
import com.vladbakalo.location_alarm.data.models.Alarm

class AlarmData() {
    var id: Long = 0
    var locationAlarmId: Long = 0
    val distance: MutableLiveData<String> = MutableLiveData()
    val enabled: MutableLiveData<Boolean> = MutableLiveData()

    init {
        enabled.postValue(true)
    }

    constructor(model: Alarm): this(){
        id = model.id
        locationAlarmId = model.locationAlarmId
        distance.postValue(model.notifyDistanceMeters.toString())
        enabled.postValue(model.enabled)
    }

    fun toAlarmModel(): Alarm{
        return Alarm(id, enabled.value!!, locationAlarmId, distance.value!!.toInt())
    }

    override fun equals(other: Any?): Boolean {
        if (other != null && other is AlarmData){
            return this.distance == other.distance
        }
        return super.equals(other)
    }

    override fun hashCode(): Int {
        return distance.hashCode()
    }
}