package com.vladbakalo.location_alarm.ui.auth.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders

import com.vladbakalo.location_alarm.R
import com.vladbakalo.location_alarm.base.BaseFragment
import com.vladbakalo.location_alarm.databinding.FragmentLoginBinding

class LoginFragment : BaseFragment(){

    lateinit var viewModel: LoginFragmentViewModel
    lateinit var binding: FragmentLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(LoginFragmentViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false)

        binding.viewModel = viewModel
        binding.executePendingBindings()
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

    override fun onBackPressed() {
//        presenter.onBackPressed()
    }


//    fun getEmailText(): String {
//    }
//
//    fun getPasswordText(): String {
//    }
//
//    fun validateInputFields(): Boolean {
//    }

    companion object{
        fun newInstance(): LoginFragment{
            return LoginFragment()
        }
    }
}
