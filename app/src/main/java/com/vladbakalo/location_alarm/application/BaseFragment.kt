package com.vladbakalo.location_alarm.application

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.vladbakalo.location_alarm.di.component.DaggerPresenterComponent
import com.vladbakalo.location_alarm.di.module.FragmentModule

class BaseFragment : Fragment(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        injectDependencies()
    }

    private fun injectDependencies(){
        DaggerPresenterComponent.builder()
            .fragmentModule(FragmentModule())
            .build()
            .inject(this)
    }
}
