package com.lydone.okna_service_android_app.presentation.profile.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavDirections
import com.lydone.okna_service_android_app.domain.interactor.ProfileInteractor
import com.lydone.okna_service_android_app.domain.model.Order
import com.lydone.okna_service_android_app.domain.model.UserInfo
import com.lydone.okna_service_android_app.presentation.core.SingleLiveEvent
import com.lydone.okna_service_android_app.presentation.core.StateLiveData
import com.lydone.okna_service_android_app.presentation.core.StateMutableLiveData
import com.lydone.okna_service_android_app.presentation.profile.fragment.ProfileFragmentDirections
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(private val interactor: ProfileInteractor) : ViewModel() {

    private val dataStateMutableLiveData = StateMutableLiveData<Data>()
    val dataStateLiveData: StateLiveData<Data> get() = dataStateMutableLiveData

    private val navDirectionsMutableLiveData = SingleLiveEvent<NavDirections>()
    val navDirectionsLiveData: LiveData<NavDirections> get() = navDirectionsMutableLiveData

    init {
        loadData()
    }

    fun loadData() {
        viewModelScope.launch {
            dataStateMutableLiveData.setLoadingState()
            try {
                dataStateMutableLiveData.setSuccessState(
                    Data(
                        userInfo = interactor.getUserInfo(),
                        orders = interactor.getOrders()
                    )
                )
            } catch (e: Exception) {
                navDirectionsMutableLiveData.value = ProfileFragmentDirections.startLoginGraph()
            }
        }
    }

    fun onLogoutMenuItemClicked() {
        interactor.logout()
        navDirectionsMutableLiveData.value = ProfileFragmentDirections.logoutAction()
    }

    data class Data(
        val userInfo: UserInfo,
        val orders: List<Order>,
    )


}