package com.vladbakalo.location_alarm_list.di.module

import androidx.lifecycle.ViewModel
import com.vladbakalo.core.di.common.ViewModelKey
import com.vladbakalo.location_alarm_list.ui.fragment.list.LocationAlarmListViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
internal abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(LocationAlarmListViewModel::class)
    internal abstract fun alarmListViewModel(viewModel: LocationAlarmListViewModel): ViewModel
}