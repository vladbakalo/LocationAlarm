package com.vladbakalo.location_alarm.ui.tab

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.vladbakalo.location_alarm.R
import com.vladbakalo.location_alarm.application.base.BaseActivity
import com.vladbakalo.location_alarm.application.base.BaseFragment
import com.vladbakalo.location_alarm.common.BackButtonListener
import com.vladbakalo.location_alarm.navigation.LocalCiceroneHolder
import com.vladbakalo.location_alarm.navigation.Screens
import com.vladbakalo.location_alarm.navigation.TabAppNavigator
import com.vladbakalo.location_alarm.navigation.common.NavigationRouterProvider
import ru.terrakok.cicerone.Cicerone
import ru.terrakok.cicerone.Navigator
import ru.terrakok.cicerone.Router
import javax.inject.Inject

class TabContainerFragment :BaseFragment(),
    NavigationRouterProvider {

    private var isCurrentFragmentIsRoot = true

    private val navigator: Navigator by lazy {
        object : TabAppNavigator(activity, childFragmentManager, R.id.fragmentTabFlContainer){
            override fun onCurrentScreenChanged(isRoot: Boolean) {
                isCurrentFragmentIsRoot = isRoot
                (activity as BaseActivity).setShowBackButton(!isCurrentFragmentIsRoot)
            }
        }
    }

    @Inject
    lateinit var localCiceroneHolder: LocalCiceroneHolder

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        savedInstanceState?.let {
            isCurrentFragmentIsRoot = savedInstanceState.getBoolean(KEY_IS_CURRENT_ROOT, true)
        }
    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_tab_container, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        if (childFragmentManager.fragments.isEmpty()) {
            getRouter().replaceScreen(Screens.TabScreen.getTabScreenByTag(getContainerTag()))
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putBoolean(KEY_IS_CURRENT_ROOT, isCurrentFragmentIsRoot)
    }

    override fun onResume() {
        super.onResume()
        getCiceroneRouter().navigatorHolder.setNavigator(navigator)
    }

    override fun onPause() {
        super.onPause()
        getCiceroneRouter().navigatorHolder.removeNavigator()
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        if (hidden.not()){
            //When change current tab in navigator
            (activity as BaseActivity).setShowBackButton(!isCurrentFragmentIsRoot)
        }
    }

    override fun onBackPressed(): Boolean {
        val fragment = childFragmentManager.findFragmentById(R.id.fragmentTabFlContainer)

        return (fragment != null && fragment is BackButtonListener && fragment.onBackPressed())
    }

    private fun getContainerTag(): String {
        return arguments?.getString(KEY_TAB_TAG) ?: throw RuntimeException(
            "Must provide container tag for TabContainer")
    }

    private fun getCiceroneRouter(): Cicerone<Router> {
        return localCiceroneHolder.getCicerone(getContainerTag())
    }

    override fun getRouter(): Router {
        return getCiceroneRouter().router
    }

    companion object {
        private const val KEY_TAB_TAG = "KEY_TAB_TAG"
        private const val KEY_IS_CURRENT_ROOT = "KEY_IS_CURRENT_ROOT"

        fun create(tabTag: String): TabContainerFragment {
            val fragment = TabContainerFragment()
            val arg = Bundle()
            arg.putString(KEY_TAB_TAG, tabTag)

            fragment.arguments = arg
            return fragment
        }
    }
}