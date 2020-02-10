package com.vladbakalo.location_alarm.base

import android.os.Bundle
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.lifecycle.Observer
import com.google.android.material.snackbar.Snackbar
import com.vladbakalo.location_alarm.data.ErrorState

abstract class BaseVMFragment<T :BaseViewModel> :BaseFragment() {

    var viewModel: T? = null

    abstract fun createViewModel(): T

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = if (viewModel == null) createViewModel() else viewModel

        setObservers()
    }

    private fun setObservers() {
        viewModel!!.loadingStateLiveData.observe(this, Observer {

        })
        viewModel!!.errorStateLiveData.observe(this, Observer {
            Snackbar.make(view!!, it.errorText, Snackbar.LENGTH_SHORT)

        })
    }

    private fun showToast(text: String) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT)
            .show()
    }

    private fun showToast(@StringRes textResId: Int) {
        Toast.makeText(context, textResId, Toast.LENGTH_SHORT)
            .show()
    }

    private fun showError(error: ErrorState) {

    }
}
