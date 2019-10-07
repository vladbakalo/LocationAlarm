package com.vladbakalo.location_alarm.ui.auth

import com.vladbakalo.location_alarm.mvp.BaseContract

class AuthContract{

    interface View: BaseContract.View{
    }

    interface Presenter: BaseContract.Presenter<View>{
        fun onBackPressed()
    }
}