package com.vladbakalo.location_alarm.di

import android.content.Context
import com.vladbakalo.core.di.CoreDeps
import com.vladbakalo.location_alarm.di.module.AppModule
import com.vladbakalo.location_alarm.di.module.ViewModelModule
import com.vladbakalo.location_alarm.ui.activity.main.MainActivity
import dagger.BindsInstance
import dagger.Component
import javax.inject.Scope

@ApplicationScope
@Component(modules = [ViewModelModule::class,

    AppModule::class])
interface ApplicationComponent: CoreDeps {

    override val context: Context

    fun inject(mainActivity: MainActivity)

    @Component.Builder
    interface Builder{

        @BindsInstance
        fun bindContext(context: Context): Builder

        fun build(): ApplicationComponent
    }
}

@Scope
internal annotation class ApplicationScope