package com.vladbakalo.location_alarm.ui.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.ViewModelProviders
import com.vladbakalo.location_alarm.base.BaseVMFragment

class AlarmListFragment :BaseVMFragment<AlarmListViewModel>() {

    override fun createViewModel(): AlarmListViewModel {
        return ViewModelProviders.of(this)
            .get(AlarmListViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = TextView(context)
        view.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT)
        view.text = TAG
        return view
    }


    companion object {
        const val TAG = "AlarmListFragment"

        fun create(): AlarmListFragment {
            return AlarmListFragment()
        }
    }
}