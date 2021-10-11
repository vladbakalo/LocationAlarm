package com.vladbakalo.location_alarm.di.module

import androidx.lifecycle.ViewModel
import com.vladbakalo.core.di.common.ViewModelKey
import com.vladbakalo.location_alarm.ui.activity.main.MainViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    internal abstract fun mainActivityViewModule(viewModel: MainViewModel): ViewModel
}