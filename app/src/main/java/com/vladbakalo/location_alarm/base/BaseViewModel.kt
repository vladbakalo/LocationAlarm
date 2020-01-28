package com.vladbakalo.location_alarm.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.vladbakalo.location_alarm.data.ErrorState
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

abstract class BaseViewModel: ViewModel(){
    val loadingStateLiveData: MutableLiveData<Boolean> = MutableLiveData()
    val errorStateLiveData: MutableLiveData<ErrorState> = MutableLiveData()

    private val compositeDisposable = CompositeDisposable()

    fun addDisposable(d: Disposable){
        compositeDisposable.add(d)
    }

    override fun onCleared() {
        compositeDisposable.dispose()
        super.onCleared()
    }
}