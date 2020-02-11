package com.vladbakalo.location_alarm.ui.tab

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.vladbakalo.location_alarm.R
import com.vladbakalo.location_alarm.base.BaseFragment
import com.vladbakalo.location_alarm.common.BackButtonListener
import com.vladbakalo.location_alarm.common.utils.ActivityUtils
import com.vladbakalo.location_alarm.navigation.LocalCiceroneHolder
import com.vladbakalo.location_alarm.navigation.Screens
import com.vladbakalo.location_alarm.navigation.common.NavigationRouterProvider
import ru.terrakok.cicerone.Cicerone
import ru.terrakok.cicerone.Navigator
import ru.terrakok.cicerone.Router
import ru.terrakok.cicerone.android.support.SupportAppNavigator
import ru.terrakok.cicerone.commands.Command
import javax.inject.Inject

class TabContainerFragment :BaseFragment(), NavigationRouterProvider {

    private val navigator: Navigator by lazy {
        object : SupportAppNavigator(activity, childFragmentManager, R.id.fragmentTabFlContainer){
            override fun setupFragmentTransaction(command: Command?,
                                                  currentFragment: Fragment?,
                                                  nextFragment: Fragment?,
                                                  fragmentTransaction: FragmentTransaction?) {
                if (currentFragment == null){
                    return
                }
                fragmentTransaction?.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_in_right)
                ActivityUtils.hideKeyboard(activity)
            }
        }
    }

    @Inject
    lateinit var localCiceroneHolder: LocalCiceroneHolder

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

    override fun onResume() {
        super.onResume()
        getCiceroneRouter().navigatorHolder.setNavigator(navigator)
    }

    override fun onPause() {
        super.onPause()
        getCiceroneRouter().navigatorHolder.removeNavigator()
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

        fun create(tabTag: String): TabContainerFragment {
            val fragment = TabContainerFragment()
            val arg = Bundle()
            arg.putString(KEY_TAB_TAG, tabTag)

            fragment.arguments = arg
            return fragment
        }
    }
}