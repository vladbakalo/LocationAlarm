package com.vladbakalo.location_alarm.ui.alarm_create

import androidx.lifecycle.MutableLiveData
import com.vladbakalo.location_alarm.base.BaseViewModel

class LocationAlarmCreateViewModel :BaseViewModel() {
    public var name: MutableLiveData<String> = MutableLiveData()
    public var address: MutableLiveData<String> = MutableLiveData()
    public var latitude: MutableLiveData<String> = MutableLiveData()
    public var longitude: MutableLiveData<String> = MutableLiveData()
    public var note: MutableLiveData<String> = MutableLiveData()
}
