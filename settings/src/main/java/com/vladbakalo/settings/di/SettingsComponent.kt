package com.vladbakalo.settings.di

import android.app.Application
import android.content.Context
import dagger.Component
import javax.inject.Scope

@SettingsScope
@Component(dependencies = [SettingsDeps::class])
internal interface SettingsComponent {

    @Component.Builder
    interface Builder{

        fun deps(deps: SettingsDeps): Builder

        fun build(): SettingsComponent
    }
}

interface SettingsDeps{
    fun context(): Context
}

interface SettingsDepsProvider {
    val settingsDeps: SettingsDeps
}

val Context.settingsDepsProvider: SettingsDepsProvider
    get() = when(this) {
        is SettingsDepsProvider -> this
        is Application -> error("Applications must implement SettingsDepsProvider")
        else -> this.applicationContext.settingsDepsProvider
    }


@Scope
internal annotation class SettingsScope