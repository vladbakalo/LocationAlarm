package com.vladbakalo.location_alarm.ui.auth.registration

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.vladbakalo.location_alarm.R
import com.vladbakalo.location_alarm.application.BaseFragment
import com.vladbakalo.location_alarm.di.component.ActivityComponent
import com.vladbakalo.location_alarm.di.module.FragmentModule
import ru.terrakok.cicerone.Router
import javax.inject.Inject

class RegistrationFragment: BaseFragment(), RegistrationContract.View {

    @Inject
    lateinit var presenter: RegistrationPresenter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_registration, container, false)
    }

    override fun onBackPressed() {
        presenter.onBackPressed()
    }

    override fun injectDependencies() {
        ActivityComponent.activityComponent?.plusFragmentComponent(FragmentModule())?.inject(this)
    }

    companion object{
        fun newInstance(): RegistrationFragment{
            return RegistrationFragment()
        }
    }
}