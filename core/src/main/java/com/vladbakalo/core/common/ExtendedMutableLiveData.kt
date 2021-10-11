package com.vladbakalo.core.common

import androidx.annotation.MainThread
import androidx.annotation.Nullable
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer

/**
 * Has methods to set/post value without notifying it observers
 */
class ExtendedMutableLiveData<T> (value: T?) : MutableLiveData<T>(value) {

    private var needToNotify: Boolean = true

    @MainThread
    override fun observe(owner: LifecycleOwner, observer: Observer<in T>) {
        // Observe the internal MutableLiveData
        super.observe(owner, Observer<T> { t ->
            if (needToNotify) {
                observer.onChanged(t)
            }
        })
    }

    @MainThread
    override fun setValue(@Nullable t: T?) {
        needToNotify = true
        super.setValue(t)
    }

    override fun postValue(value: T?) {
        needToNotify = true
        super.postValue(value)
    }

    @MainThread
    fun setValueWithoutNotify(@Nullable t: T?){
        needToNotify = false
        super.setValue(t)
    }

    fun postValueWithoutNotify(value: T?) {
        needToNotify = false
        super.postValue(value)
    }
}