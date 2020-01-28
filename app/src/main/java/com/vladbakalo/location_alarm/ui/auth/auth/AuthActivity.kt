package com.vladbakalo.location_alarm.ui.auth.auth

import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import com.vladbakalo.location_alarm.R
import com.vladbakalo.location_alarm.base.BaseActivity
import com.vladbakalo.location_alarm.base.BaseFragment
import ru.terrakok.cicerone.Navigator
import ru.terrakok.cicerone.android.support.SupportAppNavigator
import javax.inject.Inject
import com.vladbakalo.location_alarm.ui.auth.login.LoginFragment


class AuthActivity : BaseActivity(){

    private val currentFragment: BaseFragment?
        get() = supportFragmentManager.findFragmentById(R.id.authFlContainer) as BaseFragment

    override val navigator: Navigator
        get() = SupportAppNavigator(this, R.id.authFlContainer)

    @Inject lateinit var viewModelFactory: AuthActivityViewModelFactory
    private lateinit var viewViewModel: AuthActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)

        viewViewModel = ViewModelProviders.of(this, viewModelFactory).get(AuthActivityViewModel::class.java)
        supportFragmentManager.beginTransaction()
            .replace(R.id.authFlContainer, LoginFragment(), "LoginFragment")
            .commit()
    }

    override fun onBackPressed() {
//        currentFragment?.onBackPressed() ?: presenter.onBackPressed()
    }
}
