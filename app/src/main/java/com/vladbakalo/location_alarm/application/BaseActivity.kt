package com.vladbakalo.location_alarm.application

import dagger.android.support.DaggerAppCompatActivity
import ru.terrakok.cicerone.Navigator
import ru.terrakok.cicerone.NavigatorHolder
import javax.inject.Inject

abstract class BaseActivity: DaggerAppCompatActivity(){

    @Inject lateinit var navigationHolder: NavigatorHolder
    protected abstract val navigator: Navigator

    override fun onResume() {
        super.onResume()
        navigationHolder.setNavigator(navigator)
    }

    override fun onPause() {
        navigationHolder.removeNavigator()
        super.onPause()
    }
}
