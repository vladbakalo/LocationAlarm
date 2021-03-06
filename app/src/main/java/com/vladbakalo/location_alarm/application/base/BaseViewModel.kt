package com.vladbakalo.location_alarm.application.base

import androidx.annotation.CallSuper
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.vladbakalo.location_alarm.common.MyLogger
import com.vladbakalo.location_alarm.data.ErrorState
import com.vladbakalo.location_alarm.manager.FirebaseAnalyticsManager
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import javax.inject.Inject

abstract class BaseViewModel :ViewModel() {
    @Inject
    lateinit var firebaseAnalyticsManager: FirebaseAnalyticsManager

    val loadingStateLiveData = MutableLiveData<Boolean>()
    val infoMessageLiveData = MutableLiveData<String>()
    val errorStateLiveData = MutableLiveData<ErrorState>()

    private val compositeDisposable = CompositeDisposable()

    init {

    }

    open fun onBackButtonClick(): Boolean{
        return false
    }

    fun onBaseError(error: Throwable, tag: String){
        MyLogger.logException(tag, error)
        error.message?.let {
            errorStateLiveData.postValue(ErrorState(it))
        }
    }

    fun addDisposable(d: Disposable) {
        compositeDisposable.add(d)
    }

    @CallSuper
    override fun onCleared() {
        compositeDisposable.dispose()
        super.onCleared()
    }
}