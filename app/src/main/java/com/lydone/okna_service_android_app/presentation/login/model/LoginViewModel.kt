package com.lydone.okna_service_android_app.presentation.login.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lydone.okna_service_android_app.domain.interactor.LoginInteractor
import com.lydone.okna_service_android_app.presentation.core.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val interactor: LoginInteractor) : ViewModel() {

    private val isProgressShownMutableLiveData = MutableLiveData(false)
    val isProgressShownLiveData: LiveData<Boolean> get() = isProgressShownMutableLiveData

    private val navigateToSmsCodeMutableLiveData = SingleLiveEvent<Unit>()
    val navigateToSmsCodeLiveData: LiveData<Unit> get() = navigateToSmsCodeMutableLiveData

    private val phoneNumberMutableLiveData = MutableLiveData<String>()
    val phoneNumberLiveData: LiveData<String> get() = phoneNumberMutableLiveData

    var phoneNumber: String?
        get() = phoneNumberMutableLiveData.value
        set(value) {
            phoneNumberMutableLiveData.value = requireNotNull(value)
        }

    fun onSendSmsCodeButtonClicked() {
        viewModelScope.launch {
            isProgressShownMutableLiveData.value = true
            interactor.sendSmsCode(requireNotNull(phoneNumber))
            isProgressShownMutableLiveData.value = false
            navigateToSmsCodeMutableLiveData.value = Unit
        }
    }
}