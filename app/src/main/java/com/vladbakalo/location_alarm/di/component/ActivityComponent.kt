package com.vladbakalo.location_alarm.di.component

import com.vladbakalo.location_alarm.di.module.ActivityModule
import com.vladbakalo.location_alarm.di.module.FragmentModule
import com.vladbakalo.location_alarm.di.scope.PerActivityScope
import com.vladbakalo.location_alarm.ui.auth.AuthActivity
import dagger.Subcomponent

@PerActivityScope
@Subcomponent (modules = [ActivityModule::class])
interface ActivityComponent {
    fun inject(activity: AuthActivity)

    fun plusFragmentComponent(fragmentModule: FragmentModule): FragmentComponent

    companion object{
        var activityComponent: ActivityComponent? = null
    }
}