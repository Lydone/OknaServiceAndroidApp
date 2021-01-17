package com.lydone.okna_service_android_app.presentation.calculator.model

import androidx.lifecycle.*
import com.lydone.okna_service_android_app.domain.calculator.CalculatorInteractor
import com.lydone.okna_service_android_app.domain.calculator.model.GlassUnitType
import com.lydone.okna_service_android_app.domain.calculator.model.HouseType
import com.lydone.okna_service_android_app.domain.calculator.model.MaterialType
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CalculatorViewModel @Inject constructor(
    private val interactor: CalculatorInteractor
) : ViewModel() {

//    private val windowSashesCountMutableLiveData = MutableLiveData(WindowSashesCount.ONE)

    private val windowWidthMutableLiveData = MutableLiveData(0)
    val windowWidthLiveData: LiveData<Int> get() = windowWidthMutableLiveData

    private val windowHeightMutableLiveData = MutableLiveData(0)
    val windowHeightLiveData: LiveData<Int> get() = windowHeightMutableLiveData

    private val sashTypesMutableLiveData = MutableLiveData(listOf(SashType.FIXED))
    val sashTypesLiveData: LiveData<List<SashType>> get() = sashTypesMutableLiveData

    private val materialTypeMutableLiveData = MutableLiveData(MaterialType.BUDGET)
    val materialTypeLiveData: LiveData<MaterialType> get() = materialTypeMutableLiveData

    val windowSizeLimitsLiveData = sashTypesMutableLiveData.switchMap {
        liveData {
            emit(interactor.getWindowSizeLimits(it.size))
        }
    }

    var sashTypes: List<SashType>
        get() = sashTypesMutableLiveData.value!!
        set(value) {
            sashTypesMutableLiveData.value = value
        }

    var windowWidth: Int
        get() = windowWidthMutableLiveData.value!!
        set(value) {
            windowWidthMutableLiveData.value = value
        }

    var windowHeight: Int
        get() = windowHeightMutableLiveData.value!!
        set(value) {
            windowHeightMutableLiveData.value = value
        }

    var materialType: MaterialType
        get() = materialTypeMutableLiveData.value!!
        set(value) {
            materialTypeMutableLiveData.value = value
        }

    var glassUnitType: GlassUnitType = GlassUnitType.SINGLE_CHAMBERED

    var houseType : HouseType = HouseType.PREFAB

    fun updateSashesNumber(number: Int) {
        when {
            sashTypes.size > number -> sashTypes = sashTypes.subList(0, number)
            sashTypes.size < number -> sashTypes = sashTypes + List(number - sashTypes.size) { SashType.FIXED }
        }
    }

    fun onSashTypeChanged(position: Int, newType: SashType) {
        sashTypes = sashTypes.subList(0, position) + newType + sashTypes.subList(position + 1, sashTypes.size)
    }
}