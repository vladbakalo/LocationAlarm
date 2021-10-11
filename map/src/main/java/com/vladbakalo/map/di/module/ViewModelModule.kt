package com.vladbakalo.map.di.module

import androidx.lifecycle.ViewModel
import com.vladbakalo.core.di.common.ViewModelKey
import com.vladbakalo.map.ui.fragment.map.MapViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
internal abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(MapViewModel::class)
    internal abstract fun alarmMapViewModel(viewModel: MapViewModel): ViewModel
}