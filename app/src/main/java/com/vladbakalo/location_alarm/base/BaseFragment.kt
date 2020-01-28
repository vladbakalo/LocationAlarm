package com.vladbakalo.location_alarm.base

import android.os.Bundle
import android.widget.Toast
import androidx.annotation.RawRes
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.vladbakalo.location_alarm.common.BackButtonListener
import com.vladbakalo.location_alarm.data.ErrorState

abstract class BaseFragment<T: BaseViewModel> : Fragment(),
    BackButtonListener{

    lateinit var viewModel: T

    abstract fun createViewModel(): T

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = createViewModel()

        setObservers()
    }

    private fun setObservers() {
        viewModel.loadingStateLiveData.observe(this, Observer {

        })
        viewModel.errorStateLiveData.observe(this, Observer {

        })
    }

    private fun showToast(text: String){
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
    }

    private fun showToast(@StringRes textResId: Int){
        Toast.makeText(context, textResId, Toast.LENGTH_SHORT).show()
    }

    private fun showError(error: ErrorState){

    }
}
