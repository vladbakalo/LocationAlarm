package com.vladbakalo.location_alarm.di.module

import com.vladbakalo.location_alarm.ui.main.MainActivity
import com.vladbakalo.location_alarm.ui.main.MainActivityViewModelFactory
import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector

@Module
internal abstract class MainActivityModule {

    @ContributesAndroidInjector
    internal abstract fun mainActivity(): MainActivity

    @Module
    companion object {

        @JvmStatic
        @Provides
        internal fun providesMainViewModelFactory()
                : MainActivityViewModelFactory {
            return MainActivityViewModelFactory()
        }
    }
}