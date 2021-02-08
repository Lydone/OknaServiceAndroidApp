package com.lydone.okna_service_android_app.presentation.cart.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.lydone.okna_service_android_app.domain.interactor.CartInteractor
import com.lydone.okna_service_android_app.domain.model.Window
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val cartInteractor: CartInteractor
) : ViewModel() {

    val windowsLiveData = cartInteractor.getWindows().asLiveData()

    fun onDeleteWindowButtonClicked(window: Window) = viewModelScope.launch { cartInteractor.deleteWindow(window) }
}