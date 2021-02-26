package com.lydone.okna_service_android_app.presentation.registration.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavDirections
import com.lydone.okna_service_android_app.LoginGraphDirections
import com.lydone.okna_service_android_app.domain.interactor.RegistrationInteractor
import com.lydone.okna_service_android_app.presentation.core.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegistrationViewModel @Inject constructor(private val interactor: RegistrationInteractor) : ViewModel() {

    private val isProgressShownMutableLiveData = MutableLiveData(false)
    val isProgressShownLiveData: LiveData<Boolean> get() = isProgressShownMutableLiveData

    private var isProgressShown
        get() = isProgressShownMutableLiveData.value!!
        set(value) {
            isProgressShownMutableLiveData.value = value
            toggleSignUpButtonEnabledState(name = name, email = email, isProgressShown = value)
        }

    private val navDirectionsMutableLiveData = SingleLiveEvent<NavDirections>()
    val navDirectionsLiveData: LiveData<NavDirections> get() = navDirectionsMutableLiveData

    private val isSignUpButtonEnabledMutableLiveData = MutableLiveData(false)
    val isSignUpButtonEnabledLiveData: LiveData<Boolean> get() = isSignUpButtonEnabledMutableLiveData

    private val nameMutableLiveData = MutableLiveData("")
    val nameLiveData: LiveData<String> get() = nameMutableLiveData

    var name
        get() = nameMutableLiveData.value!!
        set(value) {
            nameMutableLiveData.value = value
            toggleSignUpButtonEnabledState(name = value, email = email, isProgressShown = isProgressShown)
        }

    private val emailMutableLiveData = MutableLiveData("")
    val emailLiveData: LiveData<String> get() = emailMutableLiveData

    var email
        get() = emailMutableLiveData.value!!
        set(value) {
            emailMutableLiveData.value = value
            toggleSignUpButtonEnabledState(name = name, email = value, isProgressShown = isProgressShown)
        }

    var phoneNumber: String? = null

    var smsCode: String? = null

    fun onSignUpButtonClicked() {
        viewModelScope.launch {
            isProgressShownMutableLiveData.value = true
            interactor.signUp(
                phoneNumber = requireNotNull(phoneNumber),
                smsCode = requireNotNull(smsCode),
                name = name,
                email = email
            )
            isProgressShownMutableLiveData.value = false
            navDirectionsMutableLiveData.value = LoginGraphDirections.popLoginGraph()
        }
    }

    private fun toggleSignUpButtonEnabledState(name: String, email: String, isProgressShown: Boolean) {
        isSignUpButtonEnabledMutableLiveData.value =
            name.isNotBlank() && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
                    && !isProgressShown
    }

}