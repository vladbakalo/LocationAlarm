package com.vladbakalo.core.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.vladbakalo.core.common.SingleLiveEvent
import com.vladbakalo.core.data.ErrorState
import com.vladbakalo.core.navigation.NavigationEvent
import kotlinx.coroutines.CoroutineExceptionHandler

abstract class BaseViewModel :ViewModel() {
    val loadingStateLiveData = MutableLiveData<Boolean>()
    val infoMessageLiveData = MutableLiveData<String>()
    val errorStateLiveData = MutableLiveData<ErrorState>()
    val navigationEventSingleLiveData = SingleLiveEvent<NavigationEvent>()

    val baseExceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
        throwable.message?.let {
            errorStateLiveData.postValue(ErrorState(it))
        }
    }

    fun onBaseError(error: Throwable) {
//        MyLogger.logException(tag, error)
        error.message?.let {
            errorStateLiveData.postValue(ErrorState(it))
        }
    }
}