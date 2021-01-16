package com.lydone.okna_service_android_app.presentation.calculator.model

import android.util.Log
import android.view.View
import androidx.annotation.IdRes
import androidx.lifecycle.*
import com.lydone.okna_service_android_app.R
import com.lydone.okna_service_android_app.domain.calculator.CalculatorInteractor
import com.lydone.okna_service_android_app.domain.calculator.data.WindowSizeLimits
import com.lydone.okna_service_android_app.models.data.TestBean
import com.lydone.okna_service_android_app.presentation.calculator.converter.ChipIdToWindowSashesCountConverter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
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

    private val windowSashesCountMutableLiveData = MutableLiveData(WindowSashesCount.ONE)
    val windowSashesCountLiveData: LiveData<WindowSashesCount> get() = windowSashesCountMutableLiveData

    val windowSizeLimitsLiveData = windowSashesCountMutableLiveData.switchMap {
        liveData {
            emit(interactor.getWindowSizeLimits(it))
        }
    }

    var windowSashesCount: WindowSashesCount
        get() = windowSashesCountMutableLiveData.value!!
        set(value) {
            windowSashesCountMutableLiveData.value = value
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
}