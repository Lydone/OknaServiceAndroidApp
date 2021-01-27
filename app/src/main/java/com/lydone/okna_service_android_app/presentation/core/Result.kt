package com.lydone.okna_service_android_app.presentation.core

sealed class Result<T> {

    class Loading<T> : Result<T>()

    data class Error<T>(val exception: Exception) : Result<T>()

    data class Success<T>(val data: T) : Result<T>()
}