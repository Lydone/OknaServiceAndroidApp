package com.lydone.okna_service_android_app.presentation.core

import androidx.lifecycle.MutableLiveData

class StateMutableLiveData<T> : MutableLiveData<State<T>>() {

    fun setLoadingState() {
        value = State.Loading()
    }

    fun setSuccessState(data: T) {
        value = State.Success(data)
    }

    fun setErrorState(exception: Exception) {
        value = State.Error(exception)
    }

}