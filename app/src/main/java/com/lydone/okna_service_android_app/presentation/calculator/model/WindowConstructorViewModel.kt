package com.lydone.okna_service_android_app.presentation.calculator.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lydone.okna_service_android_app.domain.interactor.CalculatorInteractor
import com.lydone.okna_service_android_app.domain.model.*
import com.lydone.okna_service_android_app.presentation.core.StateLiveData
import com.lydone.okna_service_android_app.presentation.core.StateMutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WindowConstructorViewModel @Inject constructor(
    private val interactor: CalculatorInteractor
) : ViewModel() {

    private var width: Int? = null

    private var height: Int? = null

    private val materialTypeMutableLiveData = MutableLiveData(MaterialType.BUDGET)
    val materialTypeLiveData: LiveData<MaterialType> get() = materialTypeMutableLiveData

    var materialType: MaterialType
        get() = materialTypeMutableLiveData.value!!
        set(value) {
            materialTypeMutableLiveData.value = value
            loadPrice()
        }

    private val windowTypeMutableLiveData = MutableLiveData(WindowType.ONE_SASH)
    val windowTypeLiveData: LiveData<WindowType> get() = windowTypeMutableLiveData

    var windowType: WindowType
        get() = windowTypeMutableLiveData.value!!
        set(value) {
            windowTypeMutableLiveData.value = value
            sashes = List(getAvailableSashesCount(windowType)) { SashType.FIXED }
            loadPrice()
        }

    private val sashesMutableLiveData = MutableLiveData<List<SashType>>(emptyList())
    val sashesLiveData: LiveData<List<SashType>> get() = sashesMutableLiveData

    private var sashes: List<SashType>
        get() = sashesMutableLiveData.value!!
        set(value) {
            sashesMutableLiveData.value = value
            loadPrice()
        }

    private val glassUnitTypeMutableLiveData = MutableLiveData(GlassUnitType.SINGLE_CHAMBERED)
    val glassUnitTypeLiveData: LiveData<GlassUnitType> get() = glassUnitTypeMutableLiveData

    var glassUnitType: GlassUnitType
        get() = glassUnitTypeMutableLiveData.value!!
        set(value) {
            glassUnitTypeMutableLiveData.value = value
            loadPrice()
        }

    private val houseTypeMutableLiveData = MutableLiveData(HouseType.PREFAB)
    val houseTypeLiveData: LiveData<HouseType> get() = houseTypeMutableLiveData

    var houseType: HouseType
        get() = houseTypeMutableLiveData.value!!
        set(value) {
            houseTypeMutableLiveData.value = value
            loadPrice()
        }

    private val isWindowsillCheckedMutableLiveData = MutableLiveData(false)
    val isWindowsillCheckedLiveData: LiveData<Boolean> get() = isWindowsillCheckedMutableLiveData

    var isWindowsillChecked: Boolean
        get() = isWindowsillCheckedMutableLiveData.value!!
        set(value) {
            isWindowsillCheckedMutableLiveData.value = value
            loadPrice()
        }

    private val isEbbCheckedMutableLiveData = MutableLiveData(false)
    val isEbbCheckedLiveData: LiveData<Boolean> get() = isEbbCheckedMutableLiveData

    var isEbbChecked: Boolean
        get() = isEbbCheckedMutableLiveData.value!!
        set(value) {
            isEbbCheckedMutableLiveData.value = value
            loadPrice()
        }

    private val isSlopeCheckedMutableLiveData = MutableLiveData(false)
    val isSlopeCheckedLiveData: LiveData<Boolean> get() = isSlopeCheckedMutableLiveData

    var isSlopeChecked: Boolean
        get() = isSlopeCheckedMutableLiveData.value!!
        set(value) {
            isSlopeCheckedMutableLiveData.value = value
            loadPrice()
        }

    private val isLaminationCheckedMutableLiveData = MutableLiveData(false)
    val isLaminationCheckedLiveData: LiveData<Boolean> get() = isLaminationCheckedMutableLiveData

    var isLaminationChecked: Boolean
        get() = isLaminationCheckedMutableLiveData.value!!
        set(value) {
            isLaminationCheckedMutableLiveData.value = value
            loadPrice()
        }

    private val isMosquitoNetCheckedMutableLiveData = MutableLiveData(false)
    val isMosquitoNetCheckedLiveData: LiveData<Boolean> get() = isMosquitoNetCheckedMutableLiveData

    var isMosquitoNetChecked: Boolean
        get() = isMosquitoNetCheckedMutableLiveData.value!!
        set(value) {
            isMosquitoNetCheckedMutableLiveData.value = value
            loadPrice()
        }

    private val isMainProgressShownMutableLiveData = MutableLiveData<Boolean>()
    val isMainProgressShownLiveData: LiveData<Boolean> get() = isMainProgressShownMutableLiveData

    private val matchingWindowTypesMutableLiveData = MutableLiveData<List<WindowType>>()
    val matchingWindowTypesLiveData: LiveData<List<WindowType>> get() = matchingWindowTypesMutableLiveData

    private val priceMutableLiveData = StateMutableLiveData<Int>()
    val priceLiveData: StateLiveData<Int> get() = priceMutableLiveData

    private val navigateToCartMutableLiveData = MutableLiveData<Unit>()
    val navigateToCartLiveData: LiveData<Unit> get() = navigateToCartMutableLiveData

    private val window: Window
        get() = Window(
            width = requireNotNull(width),
            height = requireNotNull(height),
            materialType = materialType,
            windowType = windowType,
            sashes = sashes,
            glassUnitType = glassUnitType,
            houseType = houseType,
            isWindowsillIncluded = isWindowsillChecked,
            isEbbIncluded = isEbbChecked,
            isSlopeIncluded = isSlopeChecked,
            isLaminationIncluded = isLaminationChecked,
            isMosquitoNetIncluded = isMosquitoNetChecked
        )

    private var loadPriceJob: Job? = null

    private fun loadPrice() {
        loadPriceJob?.cancel()
        loadPriceJob = viewModelScope.launch {
            priceMutableLiveData.setLoadingState()
            priceMutableLiveData.setSuccessState(
                interactor.getPrice(window, isDeliveryIncluded = false, isInstallationIncluded = false)
            )
        }
    }

    fun onSashTypeChanged(position: Int, newType: SashType) {
        sashes = sashes.subList(0, position) + newType + sashes.subList(position + 1, sashes.size)
    }

    fun onFragmentAttached(width: Int, height: Int) {
        if (width != this.width || height != this.height) {
            this.width = width
            this.height = height
            loadMatchingWindowTypes(width, height, windowType)
        }
    }

    fun onAddToCartButtonClicked() = viewModelScope.launch {
        isMainProgressShownMutableLiveData.value = true
        interactor.addWindowToCart(window)
        isMainProgressShownMutableLiveData.value = false
        navigateToCartMutableLiveData.value = Unit
    }

    private fun loadMatchingWindowTypes(width: Int, height: Int, currentType: WindowType?) =
        viewModelScope.launch {
            isMainProgressShownMutableLiveData.value = true
            val types = interactor.getMatchingWindowTypes(width, height)
            matchingWindowTypesMutableLiveData.value = types
            isMainProgressShownMutableLiveData.value = false
            if (currentType != null && currentType !in types) {
                windowType = types[0]
            }
        }

    private fun getAvailableSashesCount(type: WindowType) = when (type) {
        WindowType.ONE_SASH -> 1
        WindowType.TWO_SASHES -> 2
        WindowType.THREE_SASHES -> 3
    }
}