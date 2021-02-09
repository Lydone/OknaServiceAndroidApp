package com.lydone.okna_service_android_app.presentation.core

import androidx.lifecycle.MutableLiveData

class StateMutableLiveData<T> : MutableLiveData<State<T>> {

    constructor() : super()

    constructor(state: State<T>) : super(state)

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