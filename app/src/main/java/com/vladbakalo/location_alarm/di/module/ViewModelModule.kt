package com.vladbakalo.location_alarm.di.module

import androidx.lifecycle.ViewModel
import com.vladbakalo.location_alarm.di.ViewModelKey
import com.vladbakalo.location_alarm.ui.activity.main.MainActivityViewModel
import com.vladbakalo.location_alarm.ui.fragment.alarm_create.LocationAlarmCreateViewModel
import com.vladbakalo.location_alarm.ui.fragment.distance_alarm_list.AlarmDistanceListViewModel
import com.vladbakalo.location_alarm.ui.fragment.list.AlarmListViewModel
import com.vladbakalo.location_alarm.ui.fragment.map.AlarmMapViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(MainActivityViewModel::class)
    internal abstract fun mainActivityViewModule(viewModel: MainActivityViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(AlarmMapViewModel::class)
    internal abstract fun alarmMapViewModel(viewModel: AlarmMapViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(AlarmListViewModel::class)
    internal abstract fun alarmListViewModel(viewModel: AlarmListViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(LocationAlarmCreateViewModel::class)
    internal abstract fun locationAlarmCreateViewModel(viewModel: LocationAlarmCreateViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(AlarmDistanceListViewModel::class)
    internal abstract fun alarmDistanceListViewModel(viewModel: AlarmDistanceListViewModel): ViewModel
}