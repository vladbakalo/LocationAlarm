package com.vladbakalo.location_alarm.di.module

import com.vladbakalo.location_alarm.manager.AuthManager
import com.vladbakalo.location_alarm.ui.auth.auth.AuthActivity
import com.vladbakalo.location_alarm.ui.auth.auth.AuthActivityViewModelFactory
import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector

@Module
internal abstract class AuthActivityModule {

    @ContributesAndroidInjector
    internal abstract fun authActivity(): AuthActivity

    @Module
    companion object {

        @JvmStatic
        @Provides
        internal fun providesMainViewModelFactory(authManager: AuthManager)
                : AuthActivityViewModelFactory {
            return AuthActivityViewModelFactory(
                authManager
            )
        }
    }
}