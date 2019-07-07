package com.vladbakalo.location_alarm.di.module

import com.vladbakalo.location_alarm.ui.login.LoginContract
import com.vladbakalo.location_alarm.ui.login.LoginPresenter
import dagger.Module
import dagger.Provides

@Module
class FragmentModule {

    @Provides
    fun provideLoginPresenter(): LoginContract.Presenter{
        return LoginPresenter()
    }
}