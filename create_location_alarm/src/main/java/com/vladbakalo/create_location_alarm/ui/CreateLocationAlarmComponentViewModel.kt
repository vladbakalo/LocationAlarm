package com.vladbakalo.create_location_alarm.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.vladbakalo.create_location_alarm.di.CreateLocationAlarmComponent
import com.vladbakalo.create_location_alarm.di.DaggerCreateLocationAlarmComponent
import com.vladbakalo.create_location_alarm.di.createLocationAlarmDepsProvider

class CreateLocationAlarmComponentViewModel (application: Application): AndroidViewModel(application) {

    internal val createLocationAlarmComponent: CreateLocationAlarmComponent by lazy {
        DaggerCreateLocationAlarmComponent.builder()
            .deps(application.applicationContext.createLocationAlarmDepsProvider.createLocationAlarmDeps)
            .build()
    }
}