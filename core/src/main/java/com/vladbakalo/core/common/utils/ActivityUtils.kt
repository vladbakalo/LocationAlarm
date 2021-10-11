package com.vladbakalo.core.common.utils

import android.content.Context
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.FragmentActivity

object ActivityUtils {

    fun hideKeyboard(activity: FragmentActivity?){
        val focusedView = activity?.currentFocus ?: return

        val service = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        service.hideSoftInputFromWindow(focusedView.windowToken, 0)
    }
}