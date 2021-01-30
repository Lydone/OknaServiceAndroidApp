package com.lydone.okna_service_android_app.presentation.core

sealed class State<T> {

    class Loading<T> : State<T>()

    data class Error<T>(val exception: Exception) : State<T>()

    data class Success<T>(val data: T) : State<T>()
}