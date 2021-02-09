package com.lydone.okna_service_android_app.presentation.cart.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lydone.okna_service_android_app.domain.interactor.CartInteractor
import com.lydone.okna_service_android_app.domain.model.HouseType
import com.lydone.okna_service_android_app.domain.model.Window
import com.lydone.okna_service_android_app.presentation.core.State
import com.lydone.okna_service_android_app.presentation.core.StateLiveData
import com.lydone.okna_service_android_app.presentation.core.StateMutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val interactor: CartInteractor
) : ViewModel() {

    private val windowsMutableLiveData = MutableLiveData<List<Window>>()
    val windowsLiveData: LiveData<List<Window>> get() = windowsMutableLiveData

    var windows: List<Window>?
        get() = windowsMutableLiveData.value
        set(value) {
            windowsMutableLiveData.value = value
        }

    private val priceMutableLiveData = StateMutableLiveData<Int>(State.Loading())
    val priceLiveData: StateLiveData<Int> get() = priceMutableLiveData

    private val isDeliveryIncludedMutableLiveData = MutableLiveData(false)
    val isDeliveryIncludedLiveData: LiveData<Boolean> get() = isDeliveryIncludedMutableLiveData

    var isDeliveryIncluded: Boolean
        get() = isDeliveryIncludedMutableLiveData.value!!
        set(value) {
            isDeliveryIncludedMutableLiveData.value = value
            updatePrice()
        }

    private val isInstallationIncludedMutableLiveData = MutableLiveData(false)
    val isInstallationIncludedLiveData: LiveData<Boolean> get() = isInstallationIncludedMutableLiveData

    var isInstallationIncluded: Boolean
        get() = isInstallationIncludedMutableLiveData.value!!
        set(value) {
            isInstallationIncludedMutableLiveData.value = value
            updatePrice()
        }

    private val houseTypeMutableLiveData = MutableLiveData(HouseType.PREFAB)
    val houseTypeLiveData: LiveData<HouseType> get() = houseTypeMutableLiveData

    var houseType: HouseType
        get() = houseTypeMutableLiveData.value!!
        set(value) {
            houseTypeMutableLiveData.value = value
            updatePrice()
        }

    private val priceJob: Job? = null

    init {
        viewModelScope.launch {
            interactor.getWindows().collectLatest {
                windowsMutableLiveData.value = it
                updatePrice()
            }
        }
    }

    private fun updatePrice() {
        priceJob?.cancel()
        val currentWindows = windows
        if (!currentWindows.isNullOrEmpty()) {
            priceMutableLiveData.setLoadingState()
            viewModelScope.launch {
                priceMutableLiveData.setSuccessState(
                    currentWindows.map { window ->
                        async {
                            interactor.getPrice(
                                window = window,
                                houseType = houseType,
                                isDeliveryIncluded = isDeliveryIncluded,
                                isInstallationIncluded = isInstallationIncluded
                            )
                        }
                    }.awaitAll().sum()
                )
            }
        }
    }

    fun onDeleteWindowButtonClicked(window: Window) = viewModelScope.launch { interactor.deleteWindow(window) }
}