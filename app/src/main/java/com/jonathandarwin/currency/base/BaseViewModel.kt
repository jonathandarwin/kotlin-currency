package com.jonathandarwin.currency.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

/**
 * Created By : Jonathan Darwin on March 27, 2021
 */ 
abstract class BaseViewModel: ViewModel() {
    val loading: LiveData<Boolean> = MutableLiveData<Boolean>()
}
