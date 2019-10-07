package com.vladbakalo.location_alarm.di.component

import com.vladbakalo.location_alarm.di.module.FragmentModule
import com.vladbakalo.location_alarm.di.scope.PerFragmentScope
import com.vladbakalo.location_alarm.ui.auth.login.LoginFragment
import com.vladbakalo.location_alarm.ui.auth.registration.RegistrationFragment
import dagger.Subcomponent


@PerFragmentScope
@Subcomponent(modules = [FragmentModule::class])
interface FragmentComponent {
    fun inject(loginFragment: LoginFragment)
    fun inject(registrationFragment: RegistrationFragment)
}