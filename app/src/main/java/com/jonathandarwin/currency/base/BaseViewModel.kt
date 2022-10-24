package com.jonathandarwin.currency.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

/**
 * Created By : Jonathan Darwin on March 27, 2021
 */
@Deprecated(message = "Please use ViewModel() instead.")
abstract class BaseViewModel: ViewModel() {

    var error = MutableLiveData<Throwable>()
    val loading = MutableLiveData<Boolean>()

    protected fun onError(e: Throwable) {
        loading.postValue(false)
        error.postValue(e)
    }
}
