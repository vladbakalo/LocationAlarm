package com.vladbakalo.location_alarm_list.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.vladbakalo.location_alarm_list.di.DaggerLocationAlarmListComponent
import com.vladbakalo.location_alarm_list.di.LocationAlarmListComponent
import com.vladbakalo.location_alarm_list.di.locationAlarmListDepsProvider

class LocationAlarmListComponentViewModel(application: Application): AndroidViewModel(application){

    internal val locationAlarmListComponent: LocationAlarmListComponent by lazy {
        DaggerLocationAlarmListComponent.builder()
            .deps(application.locationAlarmListDepsProvider.locationAlarmListDeps)
            .build()
    }
}