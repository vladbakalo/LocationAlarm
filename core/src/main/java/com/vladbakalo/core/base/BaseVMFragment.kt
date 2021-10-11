package com.vladbakalo.core.base

import android.os.Bundle
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.annotation.StringRes
import androidx.lifecycle.Observer
import com.google.android.material.snackbar.Snackbar

abstract class BaseVMFragment<T :BaseViewModel> (@LayoutRes contentLayoutId: Int) :BaseFragment(contentLayoutId) {

    private var baseViewModel: T? = null

    val viewModel: T by lazy { baseViewModel!! }

    abstract fun provideViewModel(): T

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        baseViewModel = if (baseViewModel == null) provideViewModel() else baseViewModel

        observeData()
    }

    private fun observeData() {
        viewModel.loadingStateLiveData.observe(this, Observer {
            // Set loading state to view
//            Snackbar.make(view!!, "Loading: $it", Snackbar.LENGTH_SHORT)
//                .show()
        })
        viewModel.errorStateLiveData.observe(this, Observer {
            Snackbar.make(requireView(), it.getErrorText(requireContext()), Snackbar.LENGTH_SHORT)
                .show()
        })
        viewModel.navigationEventSingleLiveData.observe(this, Observer {
            (requireActivity() as BaseActivity).navigateToDestination(it)
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
}
