package com.vladbakalo.location_alarm.ui.auth.login


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.vladbakalo.location_alarm.R
import com.vladbakalo.location_alarm.application.BaseFragment
import com.vladbakalo.location_alarm.di.component.ActivityComponent
import com.vladbakalo.location_alarm.di.module.FragmentModule
import kotlinx.android.synthetic.main.fragment_login.*
import ru.terrakok.cicerone.Router
import javax.inject.Inject

class LoginFragment : BaseFragment(),
    LoginContract.View {

    @Inject
    lateinit var presenter: LoginPresenter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loginButtonMain.setOnClickListener { presenter.onEmailLoginClick() }
        registerButtonMain.setOnClickListener { presenter.onRegisterClick() }
    }

    override fun onBackPressed() {
        presenter.onBackPressed()
    }

    override fun injectDependencies() {
        ActivityComponent.activityComponent?.plusFragmentComponent(FragmentModule())?.inject(this)
    }

    companion object{
        fun newInstance(): LoginFragment{
            return LoginFragment()
        }
    }
}
