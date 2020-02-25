package com.vladbakalo.location_alarm.application.base

import android.os.Bundle
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.lifecycle.Observer
import com.google.android.material.snackbar.Snackbar
import com.vladbakalo.location_alarm.navigation.common.NavigationRouterProvider
import ru.terrakok.cicerone.Router


abstract class BaseVMFragment<T :BaseViewModel> :BaseFragment() {

    private var baseViewModel: T? = null

    public val viewModel: T by lazy { baseViewModel!! }

    abstract fun createViewModel(): T

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        baseViewModel = if (baseViewModel == null) createViewModel() else baseViewModel

        setObservers()
    }

    private fun setObservers() {
        baseViewModel!!.loadingStateLiveData.observe(this, Observer {
            // Set loading state to view
//            Snackbar.make(view!!, "Loading: $it", Snackbar.LENGTH_SHORT)
//                .show()
        })
        baseViewModel!!.errorStateLiveData.observe(this, Observer {
            Snackbar.make(view!!, it.getErrorText(context!!), Snackbar.LENGTH_SHORT)
                .show()
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

    fun getNavigationRouter(): Router {
        return (parentFragment as NavigationRouterProvider).getRouter()
    }
}
