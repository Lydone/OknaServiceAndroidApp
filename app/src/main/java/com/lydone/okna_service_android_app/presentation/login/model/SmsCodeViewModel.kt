package com.lydone.okna_service_android_app.presentation.login.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavDirections
import com.lydone.okna_service_android_app.LoginGraphDirections
import com.lydone.okna_service_android_app.domain.exception.IncorrectOtpException
import com.lydone.okna_service_android_app.domain.exception.UserDoesNotExistException
import com.lydone.okna_service_android_app.domain.interactor.LoginInteractor
import com.lydone.okna_service_android_app.presentation.core.SingleLiveEvent
import com.lydone.okna_service_android_app.presentation.login.fragment.SmsCodeFragmentDirections
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SmsCodeViewModel @Inject constructor(private val interactor: LoginInteractor) : ViewModel() {

    private val isProgressShownMutableLiveData = MutableLiveData(false)
    val isProgressShownLiveData: LiveData<Boolean> get() = isProgressShownMutableLiveData

    private var isProgressShown
        get() = isProgressShownMutableLiveData.value!!
        set(value) {
            isProgressShownMutableLiveData.value = value
            toggleConfirmButton(smsCode = smsCode, isProgressShown = value)
        }

    private val errorTextMutableLiveData = MutableLiveData<String?>(null)
    val errorTextLiveData: LiveData<String?> get() = errorTextMutableLiveData

    private val navDirectionsMutableLiveData = SingleLiveEvent<NavDirections>()
    val navDirectionsLiveData: LiveData<NavDirections> get() = navDirectionsMutableLiveData

    private val setUserLoggedInFragmentResultMutableLiveData = SingleLiveEvent<Unit>()
    val setUserLoggedInFragmentResultLiveData: LiveData<Unit> get() = setUserLoggedInFragmentResultMutableLiveData

    private val isConfirmButtonEnabledMutableLiveData = MutableLiveData(false)
    val isConfirmButtonEnabledLiveData: LiveData<Boolean> get() = isConfirmButtonEnabledMutableLiveData

    var phoneNumber: String? = null

    private val smsCodeMutableLiveData = MutableLiveData("")
    val smsCodeLiveData: LiveData<String> get() = smsCodeMutableLiveData

    var smsCode: String
        get() = smsCodeMutableLiveData.value!!
        set(value) {
            smsCodeMutableLiveData.value = value
            errorTextMutableLiveData.value = null
            toggleConfirmButton(smsCode = value, isProgressShown = isProgressShown)
        }

    fun onConfirmButtonClicked(smsCode: String) {
        val notNullPhoneNumber = requireNotNull(phoneNumber)
        viewModelScope.launch {
            isProgressShown = true
            try {
                interactor.login(notNullPhoneNumber, smsCode)
                setUserLoggedInFragmentResultMutableLiveData.value = Unit
                navDirectionsMutableLiveData.value = LoginGraphDirections.popLoginGraph()

            } catch (e: IncorrectOtpException) {
                errorTextMutableLiveData.value = "Неверный код"

            } catch (e: UserDoesNotExistException) {
                navDirectionsMutableLiveData.value =
                    SmsCodeFragmentDirections.toRegistration(phoneNumber = notNullPhoneNumber, smsCode = smsCode)
            } finally {
                isProgressShown = false
            }
        }
    }

    private fun toggleConfirmButton(smsCode: String, isProgressShown: Boolean) {
        isConfirmButtonEnabledMutableLiveData.value = smsCode.isNotBlank() && !isProgressShown
    }
}