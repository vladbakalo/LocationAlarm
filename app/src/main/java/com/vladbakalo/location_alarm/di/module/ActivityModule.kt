package com.vladbakalo.location_alarm.di.module

import com.vladbakalo.location_alarm.di.scope.PerActivityScope
import com.vladbakalo.location_alarm.ui.auth.AuthPresenter
import dagger.Module
import dagger.Provides
import ru.terrakok.cicerone.Router
import javax.inject.Singleton

@Module
class ActivityModule {

    @PerActivityScope
    @Provides
    fun provideAuthPresenter(router: Router): AuthPresenter{
        return AuthPresenter(router)
    }
}