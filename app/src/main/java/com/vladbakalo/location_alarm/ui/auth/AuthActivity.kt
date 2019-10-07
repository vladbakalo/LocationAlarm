package com.vladbakalo.location_alarm.ui.auth

import android.os.Bundle
import com.vladbakalo.location_alarm.R
import com.vladbakalo.location_alarm.application.App
import com.vladbakalo.location_alarm.application.BaseActivity
import com.vladbakalo.location_alarm.application.BaseFragment
import com.vladbakalo.location_alarm.di.component.ActivityComponent
import com.vladbakalo.location_alarm.di.module.ActivityModule
import ru.terrakok.cicerone.Navigator
import javax.inject.Inject
import ru.terrakok.cicerone.android.support.SupportAppNavigator


class AuthActivity : BaseActivity(), AuthContract.View {

    private val currentFragment: BaseFragment?
        get() = supportFragmentManager.findFragmentById(R.id.authFlContainer) as BaseFragment

    override val navigator: Navigator
        get() = SupportAppNavigator(this, R.id.authFlContainer)

    @Inject
    lateinit var presenter: AuthPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)

    }

    override fun onResume() {
        super.onResume()
        presenter.start()
    }

    override fun onBackPressed() {
        currentFragment?.onBackPressed() ?: presenter.onBackPressed()
    }

    override fun injectDaggerDependencies() {
        ActivityComponent.activityComponent = App.appComponent.plusActivityComponent(ActivityModule())
        ActivityComponent.activityComponent?.inject(this)
    }

}
