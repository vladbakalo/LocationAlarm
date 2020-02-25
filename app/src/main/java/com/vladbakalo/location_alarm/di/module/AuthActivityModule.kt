package com.vladbakalo.location_alarm.di.module

import com.vladbakalo.location_alarm.manager.AuthManager
import com.vladbakalo.location_alarm.ui.auth.auth.AuthActivityViewModelFactory
import dagger.Module
import dagger.Provides

@Module
internal abstract class AuthActivityModule {

    @Provides
    internal fun providesMainViewModelFactory(authManager: AuthManager): AuthActivityViewModelFactory
            = AuthActivityViewModelFactory(authManager)
}