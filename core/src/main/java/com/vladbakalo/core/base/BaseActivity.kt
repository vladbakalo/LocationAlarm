package com.vladbakalo.core.base

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.vladbakalo.core.R
import com.vladbakalo.core.navigation.NavControllerProvider
import com.vladbakalo.core.navigation.NavigationEvent


abstract class BaseActivity :AppCompatActivity(), NavControllerProvider {

    private var isBackBackButtonVisible = false

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)

        setShowBackButton(savedInstanceState.getBoolean(KEY_IS_BACK_BUTTON_VISIBLE, false))
    }

    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
        super.onSaveInstanceState(outState, outPersistentState)
        outState.putBoolean(KEY_IS_BACK_BUTTON_VISIBLE, isBackBackButtonVisible)

    }

    abstract fun getToolBar(): Toolbar

    fun setShowBackButton(show: Boolean){
        isBackBackButtonVisible = show
        val toolbar = getToolBar()

        if (isBackBackButtonVisible) {
            toolbar.setNavigationIcon(R.drawable.ic_arrow_back_24dp)
            toolbar.setNavigationOnClickListener {
                onBackPressed()
            }
        } else {
            toolbar.navigationIcon = null
        }
    }

    fun navigateToDestination(navigationEvent: NavigationEvent){
        getNavController()?.navigate(navigationEvent.navDeepLinkRequest)
    }

    companion object{
        private const val KEY_IS_BACK_BUTTON_VISIBLE = "KEY_IS_BACK_BUTTON_VISIBLE"
    }
}
