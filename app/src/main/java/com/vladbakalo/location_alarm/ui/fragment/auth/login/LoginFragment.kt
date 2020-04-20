package com.vladbakalo.location_alarm.ui.fragment.auth.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.vladbakalo.location_alarm.R
import com.vladbakalo.location_alarm.application.base.BaseVMFragment
import com.vladbakalo.location_alarm.databinding.FragmentLoginBinding

class LoginFragment :BaseVMFragment<LoginFragmentViewModel>() {

    lateinit var binding: FragmentLoginBinding

    override fun provideViewModel(): LoginFragmentViewModel {
        return ViewModelProvider(this)
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
