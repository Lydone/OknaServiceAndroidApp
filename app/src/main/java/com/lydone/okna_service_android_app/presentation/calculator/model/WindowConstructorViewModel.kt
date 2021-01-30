package com.lydone.okna_service_android_app.presentation.calculator.model

import androidx.lifecycle.*
import com.lydone.okna_service_android_app.domain.calculator.CalculatorInteractor
import com.lydone.okna_service_android_app.domain.calculator.model.*
import com.lydone.okna_service_android_app.presentation.core.State
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WindowConstructorViewModel @Inject constructor(
    private val interactor: CalculatorInteractor
) : ViewModel() {

    private val windowModelMutableLiveData = MutableLiveData<WindowModel>()
    val windowModelLiveData: LiveData<WindowModel> get() = windowModelMutableLiveData

    private val isMainProgressShownMutableLiveData = MutableLiveData<Boolean>()
    val isMainProgressShownLiveData: LiveData<Boolean> get() = isMainProgressShownMutableLiveData

    private val matchingWindowTypesMutableLiveData = MutableLiveData<List<WindowType>>()
    val matchingWindowTypesLiveData: LiveData<List<WindowType>> get() = matchingWindowTypesMutableLiveData

    val priceLiveData = windowModelMutableLiveData.switchMap { model ->
        liveData {
            emit(State.Loading())
            emit(State.Success(interactor.getPrice(model)))
        }
    }

    fun onSashTypeChanged(position: Int, newType: SashType) {
        windowModelMutableLiveData.value?.let { model ->
            val newSashes =
                model.sashes.subList(0, position) + newType + model.sashes.subList(position + 1, model.sashes.size)
            if (newSashes != model.sashes) {
                windowModelMutableLiveData.value = model.copy(sashes = newSashes)
            }
        }
    }

    fun onFragmentAttached(model: WindowModel) {
        val oldModel = windowModelMutableLiveData.value
        if (oldModel == null) {
            windowModelMutableLiveData.value = model
            loadMatchingWindowTypes(model.width, model.height, model.windowType)
        }
    }

    fun onWindowsillCheckChanged(isChecked: Boolean) {
        windowModelMutableLiveData.value?.let { model ->
            if (isChecked != model.isWindowsillSelected) {
                windowModelMutableLiveData.value = model.copy(isWindowsillSelected = isChecked)
            }
        }
    }

    fun onEbbCheckChanged(isChecked: Boolean) {
        windowModelMutableLiveData.value?.let { model ->
            if (isChecked != model.isEbbSelected) {
                windowModelMutableLiveData.value = model.copy(isEbbSelected = isChecked)
            }
        }
    }

    fun onSlopeCheckChanged(isChecked: Boolean) {
        windowModelMutableLiveData.value?.let { model ->
            if (isChecked != model.isSlopeSelected) {
                windowModelMutableLiveData.value = model.copy(isSlopeSelected = isChecked)
            }
        }
    }

    fun onLaminationCheckChanged(isChecked: Boolean) {
        windowModelMutableLiveData.value?.let { model ->
            if (isChecked != model.isLaminationSelected) {
                windowModelMutableLiveData.value = model.copy(isLaminationSelected = isChecked)
            }
        }
    }

    fun onMosquitoNetCheckChanged(isChecked: Boolean) {
        windowModelMutableLiveData.value?.let { model ->
            if (isChecked != model.isMosquitoNetSelected) {
                windowModelMutableLiveData.value = model.copy(isMosquitoNetSelected = isChecked)
            }
        }
    }

    fun onGlassUnitTypeChanged(glassUnitType: GlassUnitType) {
        windowModelMutableLiveData.value?.let { model ->
            if (glassUnitType != model.glassUnitType) {
                windowModelMutableLiveData.value = model.copy(glassUnitType = glassUnitType)
            }
        }
    }

    fun onHouseTypeChanged(houseType: HouseType) {
        windowModelMutableLiveData.value?.let { model ->
            if (houseType != model.houseType) {
                windowModelMutableLiveData.value = model.copy(houseType = houseType)
            }
        }
    }

    fun onWindowTypeChanged(windowType: WindowType) {
        windowModelMutableLiveData.value?.let { model ->
            if (windowType != model.windowType) {
                windowModelMutableLiveData.value = model.copy(windowType = windowType)
                updateSashesNumber(windowType)
            }
        }
    }

    fun onMaterialTypeChanged(materialType: MaterialType) {
        windowModelMutableLiveData.value?.let { model ->
            if (materialType != model.materialType) {
                windowModelMutableLiveData.value = model.copy(materialType = materialType)
            }
        }
    }

    private fun loadMatchingWindowTypes(width: Int, height: Int, currentWindowType: WindowType) =
        viewModelScope.launch {
            isMainProgressShownMutableLiveData.value = true
            val types = interactor.getMatchingWindowTypes(width, height)
            matchingWindowTypesMutableLiveData.value = types
            isMainProgressShownMutableLiveData.value = false
            if (currentWindowType !in types) {
                windowModelMutableLiveData.value?.let { model ->
                    windowModelMutableLiveData.value = model.copy(windowType = types[0])
                }
            }
        }

    private fun updateSashesNumber(type: WindowType) {
        val count = when (type) {
            WindowType.ONE_SASH -> 1
            WindowType.TWO_SASHES -> 2
            WindowType.THREE_SASHES -> 3
        }
        windowModelMutableLiveData.value?.let { model ->
            val newSashes = when {
                model.sashes.size > count -> model.sashes.subList(0, count)
                model.sashes.size < count -> model.sashes + List(count - model.sashes.size) { SashType.FIXED }
                else -> model.sashes
            }
            if (newSashes != model.sashes) {
                windowModelMutableLiveData.value = model.copy(sashes = newSashes)
            }
        }
    }
}