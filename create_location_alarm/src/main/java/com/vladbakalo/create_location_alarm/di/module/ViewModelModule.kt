package com.vladbakalo.create_location_alarm.di.module

import androidx.lifecycle.ViewModel
import com.vladbakalo.core.di.common.ViewModelKey
import com.vladbakalo.create_location_alarm.ui.fragment.alarm_create.CreateLocationAlarmViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
internal abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(CreateLocationAlarmViewModel::class)
    internal abstract fun locationAlarmCreateViewModel(viewModel: CreateLocationAlarmViewModel): ViewModel
}