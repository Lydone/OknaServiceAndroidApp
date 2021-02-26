package com.lydone.okna_service_android_app.presentation.login.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lydone.okna_service_android_app.domain.interactor.SmsCodeInteractor
import com.lydone.okna_service_android_app.presentation.core.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PhoneNumberViewModel @Inject constructor(private val interactor: SmsCodeInteractor) : ViewModel() {

    private val isProgressShownMutableLiveData = MutableLiveData(false)
    val isProgressShownLiveData: LiveData<Boolean> get() = isProgressShownMutableLiveData

    private val navigateToSmsCodeMutableLiveData = SingleLiveEvent<Unit>()
    val navigateToSmsCodeLiveData: LiveData<Unit> get() = navigateToSmsCodeMutableLiveData

    private val phoneNumberMutableLiveData = MutableLiveData("")
    val phoneNumberLiveData: LiveData<String> get() = phoneNumberMutableLiveData

    private val isNextButtonEnabledMutableLiveData = MutableLiveData(false)
    val isNextButtonEnabledLiveData: LiveData<Boolean> get() = isNextButtonEnabledMutableLiveData

    var phoneNumber: String
        get() = phoneNumberMutableLiveData.value!!
        set(value) {
            phoneNumberMutableLiveData.value = value
            isNextButtonEnabledMutableLiveData.value = value.length == 10
        }

    fun onSendSmsCodeButtonClicked() {
        viewModelScope.launch {
            isProgressShownMutableLiveData.value = true
            interactor.sendSmsCode(phoneNumber)
            isProgressShownMutableLiveData.value = false
            navigateToSmsCodeMutableLiveData.value = Unit
        }
    }
}