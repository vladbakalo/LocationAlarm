package com.vladbakalo.location_alarm.di.component

import com.vladbakalo.location_alarm.application.BaseFragment
import com.vladbakalo.location_alarm.di.module.FragmentModule
import com.vladbakalo.location_alarm.ui.login.LoginFragment
import dagger.Component

@Component(modules = arrayOf(FragmentModule::class))
interface PresenterComponent {
    fun inject(baseFragment: BaseFragment)
}