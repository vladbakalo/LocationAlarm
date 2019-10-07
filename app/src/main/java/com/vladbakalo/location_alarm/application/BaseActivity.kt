package com.vladbakalo.location_alarm.application

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ru.terrakok.cicerone.Navigator
import ru.terrakok.cicerone.NavigatorHolder
import javax.inject.Inject

abstract class BaseActivity: AppCompatActivity(){

    @Inject
    lateinit var navigationHolder: NavigatorHolder

    protected abstract val navigator: Navigator

    override fun onCreate(savedInstanceState: Bundle?) {
        injectDaggerDependencies()
        super.onCreate(savedInstanceState)
    }

    override fun onResume() {
        super.onResume()
        navigationHolder.setNavigator(navigator)
    }

    override fun onPause() {
        navigationHolder.removeNavigator()
        super.onPause()
    }

    abstract fun injectDaggerDependencies()
}
