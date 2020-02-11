package com.vladbakalo.location_alarm.base

import android.os.Bundle
import com.vladbakalo.location_alarm.R
import dagger.android.support.DaggerAppCompatActivity
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.Router
import ru.terrakok.cicerone.android.support.SupportAppNavigator
import javax.inject.Inject

abstract class BaseActivity :DaggerAppCompatActivity() {

    @Inject
    lateinit var navigationHolder: NavigatorHolder
    @Inject
    lateinit var router: Router

    protected abstract val navigator: SupportAppNavigator

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
    }

    override fun onResumeFragments() {
        super.onResumeFragments()
        navigationHolder.setNavigator(navigator)
    }

    override fun onPause() {
        super.onPause()
        navigationHolder.removeNavigator()
    }
}
