package com.lydone.okna_service_android_app.presentation.calculator.model

import androidx.lifecycle.*
import com.lydone.okna_service_android_app.domain.interactor.CalculatorInteractor
import com.lydone.okna_service_android_app.domain.model.*
import com.lydone.okna_service_android_app.presentation.core.State
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WindowConstructorViewModel @Inject constructor(
    private val interactor: CalculatorInteractor
) : ViewModel() {

    private val windowMutableLiveData = MutableLiveData<Window?>(null)
    val windowLiveData: LiveData<Window?> get() = windowMutableLiveData

    private var window: Window?
        get() = windowMutableLiveData.value
        set(value) {
            windowMutableLiveData.value = value
        }

    private val matchingWindowTypesMutableLiveData = MutableLiveData<List<WindowType>>()
    val matchingWindowTypesLiveData: LiveData<List<WindowType>> get() = matchingWindowTypesMutableLiveData

    private val modeMutableLiveData = MutableLiveData<Mode>()
    val modeLiveData: LiveData<Mode> get() = modeMutableLiveData

    val priceLiveData = windowMutableLiveData.switchMap { window ->
        liveData {
            emit(State.Loading())
            if (window != null) {
                emit(
                    State.Success(
                        interactor.getPrice(
                            window = window,
                            houseType = HouseType.PREFAB,
                            isDeliveryIncluded = false,
                            isInstallationIncluded = false
                        )
                    )
                )
            }
        }
    }

    private val navigateToCartMutableLiveData = MutableLiveData<Unit>()
    val navigateToCartLiveData: LiveData<Unit> get() = navigateToCartMutableLiveData

    fun onFragmentAttached(width: Int, height: Int) {
        modeMutableLiveData.value = Mode.ADD
        val previousWindow = window
        if (previousWindow?.width != width || previousWindow.height != height) {
            window = null
            viewModelScope.launch {
                val types = interactor.getMatchingWindowTypes(width, height)
                matchingWindowTypesMutableLiveData.value = types

                window = Window(
                    id = null,
                    width = width,
                    height = height,
                    materialType = MaterialType.BUDGET,
                    windowType = types[0],
                    sashes = List(getAvailableSashesCount(types[0])) { SashType.FIXED },
                    glassUnitType = GlassUnitType.SINGLE_CHAMBERED,
                    isWindowsillIncluded = false,
                    isEbbIncluded = false,
                    isSlopeIncluded = false,
                    isLaminationIncluded = false,
                    isMosquitoNetIncluded = false
                )
            }
        }
    }

    fun onFragmentAttached(windowId: Int) {
        modeMutableLiveData.value = Mode.UPDATE
        if (windowId != window?.id) {
            window = null
            viewModelScope.launch {
                window = interactor.getWindowById(windowId).also { newWindow ->
                    matchingWindowTypesMutableLiveData.value =
                        interactor.getMatchingWindowTypes(newWindow.width, newWindow.height)
                }
            }
        }
    }

    fun onMaterialTypeChanged(type: MaterialType) {
        window = requireNotNull(window).copy(materialType = type)
    }

    fun onWindowTypeChanged(type: WindowType) {
        window = requireNotNull(window).let { oldWindow ->
            val oldSashes = oldWindow.sashes
            oldWindow.copy(
                windowType = type,
                sashes = when (val sashesCount = getAvailableSashesCount(type)) {
                    in 0..oldSashes.size -> oldSashes.take(sashesCount)
                    else -> oldSashes + List(sashesCount - oldSashes.size) { SashType.FIXED }
                }
            )
        }
    }

    fun onSashTypeChanged(position: Int, newType: SashType) {
        window = requireNotNull(window).let { oldWindow ->
            oldWindow.copy(
                sashes = oldWindow.sashes.take(position) + newType + oldWindow.sashes.subList(
                    position + 1,
                    oldWindow.sashes.size
                )
            )
        }
    }

    fun onGlassUnitTypeChanged(type: GlassUnitType) {
        window = requireNotNull(window).copy(glassUnitType = type)
    }

    fun onWindowsillCheckChanged(isChecked: Boolean) {
        window = requireNotNull(window).copy(isWindowsillIncluded = isChecked)
    }

    fun onEbbCheckChanged(isChecked: Boolean) {
        window = requireNotNull(window).copy(isEbbIncluded = isChecked)
    }

    fun onSlopeCheckChanged(isChecked: Boolean) {
        window = requireNotNull(window).copy(isSlopeIncluded = isChecked)
    }

    fun onLaminationCheckChanged(isChecked: Boolean) {
        window = requireNotNull(window).copy(isLaminationIncluded = isChecked)
    }

    fun onMosquitoNetCheckChanged(isChecked: Boolean) {
        window = requireNotNull(window).copy(isMosquitoNetIncluded = isChecked)
    }

    private fun getAvailableSashesCount(type: WindowType) = when (type) {
        WindowType.ONE_SASH -> 1
        WindowType.TWO_SASHES -> 2
        WindowType.THREE_SASHES -> 3
    }

    fun onAddToCartButtonClicked() = viewModelScope.launch {
        interactor.addWindowToCart(requireNotNull(window))
        navigateToCartMutableLiveData.value = Unit
    }

    fun onUpdateInCartButtonClicked() = viewModelScope.launch {
        interactor.updateWindowInCart(requireNotNull(window))
        navigateToCartMutableLiveData.value = Unit
    }

    enum class Mode {
        ADD, UPDATE
    }
}