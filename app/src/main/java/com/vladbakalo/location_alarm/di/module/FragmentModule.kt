package com.vladbakalo.location_alarm.di.module

import com.vladbakalo.location_alarm.di.scope.PerFragmentScope
import com.vladbakalo.location_alarm.ui.auth.login.LoginPresenter
import com.vladbakalo.location_alarm.ui.auth.registration.RegistrationPresenter
import dagger.Module
import dagger.Provides
import ru.terrakok.cicerone.Router

@Module
class FragmentModule {

    @PerFragmentScope
    @Provides
    fun provideLoginPresenter(router: Router): LoginPresenter{
        return LoginPresenter(router)
    }

    @PerFragmentScope
    @Provides
    fun provideRegisterPresenter(router: Router): RegistrationPresenter{
        return RegistrationPresenter(router)
    }
}