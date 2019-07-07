package com.vladbakalo.location_alarm.application

class BaseContract{
    interface Presenter<in T: View>{
        fun bind(view: T)

        fun start()

        fun stop()

        fun unbind()
    }

    interface View
}
