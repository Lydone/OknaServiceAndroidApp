package com.lydone.okna_service_android_app.presentation.calculator.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavDirections
import com.lydone.okna_service_android_app.domain.interactor.CalculatorInteractor
import com.lydone.okna_service_android_app.domain.model.*
import com.lydone.okna_service_android_app.presentation.calculator.fragment.WindowConstructorFragmentDirections
import com.lydone.okna_service_android_app.presentation.core.SingleLiveEvent
import com.lydone.okna_service_android_app.presentation.core.State
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WindowConstructorViewModel @Inject constructor(
    private val interactor: CalculatorInteractor
) : ViewModel() {

    var mode: Mode? = null

    private val dataStateMutableLiveData = MutableLiveData<State<Data>>(State.Loading())
    val dataStateLiveData: LiveData<State<Data>> get() = dataStateMutableLiveData

    private var dataState
        get() = dataStateMutableLiveData.value!!
        set(value) {
            dataStateMutableLiveData.value = value
        }

    private val data get() = (dataState as? State.Success)?.data

    private val navDirectionsMutableLiveData = SingleLiveEvent<NavDirections>()
    val navDirectionsLiveData: LiveData<NavDirections> get() = navDirectionsMutableLiveData

    fun onFragmentAttached(width: Int, height: Int) {
        val oldDataState = dataState
        if (oldDataState !is State.Success || oldDataState.data.window.width != width || oldDataState.data.window.height != height) {
            mode = Mode.ADD
            dataState = State.Loading()
            viewModelScope.launch {
                try {
                    val types = interactor.getMatchingWindowTypes(width, height)
                    dataState = State.Success(
                        Data(
                            matchingWindowTypes = types,
                            window = Window(
                                width = width,
                                height = height,
                                materialType = MaterialType.BUDGET,
                                windowType = WindowType.ONE_SASH,
                                sashes = List(getAvailableSashesCount(types[0])) { SashType.FIXED },
                                glassUnitType = GlassUnitType.SINGLE_CHAMBERED,
                                isWindowsillIncluded = false,
                                isEbbIncluded = false,
                                isSlopeIncluded = false,
                                isLaminationIncluded = false,
                                isMosquitoNetIncluded = false,
                            ),
                            priceState = State.Loading()
                        )
                    )
                    loadPrice()
                } catch (e: Exception) {
                    dataState = State.Error(e)
                }
            }
        }
    }

    fun onFragmentAttached(windowId: Int) {
        val oldDataState = dataState
        if (oldDataState !is State.Success || oldDataState.data.window.id != windowId) {
            mode = Mode.UPDATE
            dataState = State.Loading()
            viewModelScope.launch {
                interactor.getWindowById(windowId).let { window ->
                    try {
                        val types = interactor.getMatchingWindowTypes(window.width, window.height)
                        dataState = State.Success(
                            Data(
                                matchingWindowTypes = types,
                                window = window,
                                priceState = State.Loading()
                            )
                        )
                        loadPrice()
                    } catch (e: Exception) {
                        dataState = State.Error(e)
                    }
                }
            }
        }
    }

    fun onMaterialTypeChanged(type: MaterialType) {
        data?.let { dataState = State.Success(it.copy(window = it.window.copy(materialType = type))) }
        loadPrice()
    }

    fun onWindowTypeChanged(type: WindowType) {
        data?.let { oldData ->
            oldData.window.sashes.let { oldSashes ->
                dataState = State.Success(
                    oldData.copy(
                        window = oldData.window.copy(
                            windowType = type,
                            sashes = when (val sashesCount = getAvailableSashesCount(type)) {
                                in 0..oldSashes.size -> oldSashes.take(sashesCount)
                                else -> oldSashes + List(sashesCount - oldSashes.size) { SashType.FIXED }
                            }
                        )
                    )
                )
            }
        }
        loadPrice()
    }

    fun onSashTypeChanged(position: Int, newType: SashType) {
        data?.let { oldData ->
            oldData.window.let { oldWindow ->
                dataState = State.Success(
                    oldData.copy(
                        window = oldWindow.copy(
                            sashes = oldWindow.sashes.take(position) + newType + oldWindow.sashes.subList(
                                position + 1,
                                oldWindow.sashes.size
                            )
                        )
                    )
                )
            }
        }
        loadPrice()
    }

    fun onGlassUnitTypeChanged(type: GlassUnitType) {
        data?.let { dataState = State.Success(it.copy(window = it.window.copy(glassUnitType = type))) }
        loadPrice()
    }

    fun onWindowsillCheckChanged(isChecked: Boolean) {
        data?.let { dataState = State.Success(it.copy(window = it.window.copy(isWindowsillIncluded = isChecked))) }
        loadPrice()
    }

    fun onEbbCheckChanged(isChecked: Boolean) {
        data?.let { dataState = State.Success(it.copy(window = it.window.copy(isEbbIncluded = isChecked))) }
        loadPrice()
    }

    fun onSlopeCheckChanged(isChecked: Boolean) {
        data?.let { dataState = State.Success(it.copy(window = it.window.copy(isSlopeIncluded = isChecked))) }
        loadPrice()
    }

    fun onLaminationCheckChanged(isChecked: Boolean) {
        data?.let { dataState = State.Success(it.copy(window = it.window.copy(isLaminationIncluded = isChecked))) }
        loadPrice()
    }

    fun onMosquitoNetCheckChanged(isChecked: Boolean) {
        data?.let { dataState = State.Success(it.copy(window = it.window.copy(isMosquitoNetIncluded = isChecked))) }
        loadPrice()
    }

    private fun getAvailableSashesCount(type: WindowType) = when (type) {
        WindowType.ONE_SASH -> 1
        WindowType.TWO_SASHES -> 2
        WindowType.THREE_SASHES -> 3
    }

    fun onAddToCartButtonClicked() = viewModelScope.launch {
        data?.window?.let { interactor.addWindowToCart(it) }
        navDirectionsMutableLiveData.value = WindowConstructorFragmentDirections.actionCalculatorFragmentToCart()
    }

    fun onUpdateInCartButtonClicked() = viewModelScope.launch {
        data?.window?.let { interactor.updateWindowInCart(it) }
        navDirectionsMutableLiveData.value = WindowConstructorFragmentDirections.actionCalculatorFragmentToCart()
    }

    private fun loadPrice() {
        (dataState as? State.Success)?.data?.let { data ->
            dataState = State.Success(data.copy(priceState = State.Loading()))
            viewModelScope.launch {
                dataState = try {
                    State.Success(
                        data.copy(
                            priceState = State.Success(
                                interactor.getPrice(
                                    window = data.window,
                                    houseType = HouseType.PREFAB,
                                    isDeliveryIncluded = false,
                                    isInstallationIncluded = false,
                                )
                            )
                        )
                    )
                } catch (e: Exception) {
                    State.Success(data.copy(priceState = State.Error(e)))
                }
            }
        }
    }

    enum class Mode {
        ADD, UPDATE
    }

    data class Data(
        val matchingWindowTypes: List<WindowType>,
        val window: Window,
        val priceState: State<Int>,
    )
}