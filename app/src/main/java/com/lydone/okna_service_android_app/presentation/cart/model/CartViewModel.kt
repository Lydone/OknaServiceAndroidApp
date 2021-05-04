package com.lydone.okna_service_android_app.presentation.cart.model

import android.content.Context
import android.location.Geocoder
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavDirections
import com.google.android.gms.maps.model.LatLng
import com.lydone.okna_service_android_app.CartGraphDirections
import com.lydone.okna_service_android_app.MainGraphDirections
import com.lydone.okna_service_android_app.domain.interactor.CartInteractor
import com.lydone.okna_service_android_app.domain.model.CreateOrderParams
import com.lydone.okna_service_android_app.domain.model.HouseType
import com.lydone.okna_service_android_app.domain.model.Window
import com.lydone.okna_service_android_app.presentation.core.SingleLiveEvent
import com.lydone.okna_service_android_app.presentation.core.State
import com.lydone.okna_service_android_app.presentation.core.StateLiveData
import com.lydone.okna_service_android_app.presentation.core.StateMutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    @ApplicationContext context: Context,
    private val interactor: CartInteractor
) : ViewModel() {

    private val windowsMutableLiveData = MutableLiveData<List<Window>?>(null)
    val windowsLiveData: LiveData<List<Window>?> get() = windowsMutableLiveData

    private val isFullscreenProgressShownMutableLiveData = MutableLiveData(false)
    val isFullscreenProgressShownLiveData: LiveData<Boolean> get() = isFullscreenProgressShownMutableLiveData

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

    private val navDirectionsMutableLiveData = SingleLiveEvent<NavDirections>()
    val navDirectionsLiveData: LiveData<NavDirections> get() = navDirectionsMutableLiveData

    private var priceJob: Job? = null

    private val geocoder = Geocoder(context)

    private val deliveryAddressStringMutableLiveData = MutableLiveData<String>()
    val deliveryAddressStringLiveData: LiveData<String> get() = deliveryAddressStringMutableLiveData

    var deliveryAddressLatLng: LatLng? = null
        set(value) {
            if (value != null) {
                deliveryAddressStringMutableLiveData.value = getAddressString(value)
            }
            field = value
        }

    init {
        viewModelScope.launch {
            interactor.getWindows().collectLatest {
                windows = it
                updatePrice()
            }
        }
    }

    private fun updatePrice() {
        priceJob?.cancel()
        val currentWindows = windows
        if (!currentWindows.isNullOrEmpty()) {
            priceMutableLiveData.setLoadingState()
            priceJob = viewModelScope.launch {
                try {
                    priceMutableLiveData.setSuccessState(
                        currentWindows.map { window ->
                            async(SupervisorJob(coroutineContext[Job])) {
                                interactor.getPrice(
                                    window = window,
                                    houseType = houseType,
                                    isDeliveryIncluded = isDeliveryIncluded,
                                    isInstallationIncluded = isInstallationIncluded
                                )
                            }
                        }.awaitAll().sum()
                    )
                } catch (e: Exception) {
                    priceMutableLiveData.setErrorState(e)
                }
            }
        } else {
            priceMutableLiveData.setSuccessState(0)
        }
    }

    private fun getAddressString(latLng: LatLng) =
        geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1).firstOrNull()?.let { address ->
            buildString {
                for (i in 0..address.maxAddressLineIndex) {
                    append("${address.getAddressLine(i)},")
                    deleteCharAt(length - 1)
                }
            }
        } ?: ""


    fun onDeleteWindowButtonClicked(window: Window) = viewModelScope.launch { interactor.deleteWindow(window) }

    fun createOrder() = viewModelScope.launch {
        try {
            isFullscreenProgressShownMutableLiveData.value = true
            interactor.createOrder(
                CreateOrderParams(
                    latitude = deliveryAddressLatLng?.latitude,
                    longitude = deliveryAddressLatLng?.longitude,
                    address = deliveryAddressStringMutableLiveData.value ?: "г. Подольск, Большая Серпуховская, 34",
                    windows = requireNotNull(windows),
                    houseType = houseType,
                    isDeliveryIncluded = isDeliveryIncluded,
                    isInstallationIncluded = isInstallationIncluded
                )
            )
            navDirectionsMutableLiveData.value = CartGraphDirections.profileAction()
        } catch (e: Exception) {
            navDirectionsMutableLiveData.value = MainGraphDirections.startLoginGraph()
        } finally {
            isFullscreenProgressShownMutableLiveData.value = false
        }
    }
}