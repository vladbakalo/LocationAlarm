package com.vladbakalo.location_alarm.ui.auth.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders

import com.vladbakalo.location_alarm.R
import com.vladbakalo.location_alarm.application.base.BaseVMFragment
import com.vladbakalo.location_alarm.databinding.FragmentLoginBinding

class LoginFragment :BaseVMFragment<LoginFragmentViewModel>() {

    lateinit var binding: FragmentLoginBinding

    override fun createViewModel(): LoginFragmentViewModel {
        return ViewModelProviders.of(this)
            .get(LoginFragmentViewModel::class.java)
    }


    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false)

        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        loginButtonMain.setOnClickListener {
//            presenter.onEmailLoginClick()
//
//            val providers = arrayListOf(
//                AuthUI.IdpConfig.EmailBuilder().build(),
//                AuthUI.IdpConfig.GoogleBuilder().build())
//
//            startActivityForResult(
//                AuthUI.getInstance()
//                    .createSignInIntentBuilder()
//                    .setAvailableProviders(providers)
//                    .setLogo(R.mipmap.ic_launcher) // Set logo drawable
//                    .build(),
//                1001)
//        }
//        registerButtonMain.setOnClickListener { presenter.onRegisterClick() }
    }


//    fun getEmailText(): String {
//    }
//
//    fun getPasswordText(): String {
//    }
//
//    fun validateInputFields(): Boolean {
//    }

    companion object {
        fun create(): LoginFragment {
            return LoginFragment()
        }
    }
}
