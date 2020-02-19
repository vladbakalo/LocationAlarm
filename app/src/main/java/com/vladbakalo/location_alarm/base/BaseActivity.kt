package com.vladbakalo.location_alarm.base

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.widget.Toolbar
import com.vladbakalo.location_alarm.R
import dagger.android.support.DaggerAppCompatActivity
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.Router
import ru.terrakok.cicerone.android.support.SupportAppNavigator
import javax.inject.Inject


abstract class BaseActivity :DaggerAppCompatActivity() {

    @Inject
    lateinit var navigationHolder: NavigatorHolder
    @Inject
    lateinit var router: Router

    protected abstract val navigator: SupportAppNavigator
    private var isBackBackButtonVisible = false

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)

        setShowBackButton(savedInstanceState.getBoolean(KEY_IS_BACK_BUTTON_VISIBLE, false))
    }

    override fun onResumeFragments() {
        super.onResumeFragments()
        navigationHolder.setNavigator(navigator)
    }

    override fun onPause() {
        super.onPause()
        navigationHolder.removeNavigator()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putBoolean(KEY_IS_BACK_BUTTON_VISIBLE, isBackBackButtonVisible)
        super.onSaveInstanceState(outState)
    }

    abstract fun getToolBar(): Toolbar

    public fun setShowBackButton(show: Boolean){
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

    companion object{
        private const val KEY_IS_BACK_BUTTON_VISIBLE = "KEY_IS_BACK_BUTTON_VISIBLE"
    }
}
