package com.vladbakalo.core.common

import timber.log.Timber

object MyLogger {

    fun dt(tag: String, msg: String) {
        Timber.d("VVV-$tag", msg)
    }
}