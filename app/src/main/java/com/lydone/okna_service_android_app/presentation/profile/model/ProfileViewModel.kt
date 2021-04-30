package com.lydone.okna_service_android_app.presentation.profile.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavDirections
import com.lydone.okna_service_android_app.domain.interactor.ProfileInteractor
import com.lydone.okna_service_android_app.domain.model.UserInfo
import com.lydone.okna_service_android_app.presentation.core.SingleLiveEvent
import com.lydone.okna_service_android_app.presentation.profile.fragment.ProfileFragmentDirections
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(private val interactor: ProfileInteractor) : ViewModel() {

    private val userInfoMutableLiveData = MutableLiveData<UserInfo>()
    val userInfoLiveData: LiveData<UserInfo> get() = userInfoMutableLiveData

    private val navDirectionsMutableLiveData = SingleLiveEvent<NavDirections>()
    val navDirectionsLiveData: LiveData<NavDirections> get() = navDirectionsMutableLiveData

    init {
        loadUserInfo()
    }

    fun loadUserInfo() {
        viewModelScope.launch {
            try {
                userInfoMutableLiveData.value = interactor.getUserInfo()
            } catch (e: Exception) {
                navDirectionsMutableLiveData.value = ProfileFragmentDirections.startLoginGraph()
            }
        }
    }


}