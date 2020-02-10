package com.vladbakalo.location_alarm.ui.auth.registration

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import com.vladbakalo.location_alarm.R
import com.vladbakalo.location_alarm.base.BaseFragment

class RegistrationFragment :BaseFragment() {

    lateinit var viewModel: RegistrationFragmentViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(this)
            .get(RegistrationFragmentViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_registration, container, false)
    }


    companion object {
        fun create(): RegistrationFragment {
            return RegistrationFragment()
        }
    }
}