package com.lydone.okna_service_android_app.presentation.calculator.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lydone.okna_service_android_app.domain.interactor.CalculatorInteractor
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WindowDimensionsViewModel @Inject constructor(calculatorInteractor: CalculatorInteractor) : ViewModel() {

    // region width
    var width: Int? = null
        private set(value) {
            field = value
            updateNextButtonState()
        }

    private val widthSliderValueMutableLiveData = MutableLiveData(1f)
    val widthSliderValueLiveData: LiveData<Float> get() = widthSliderValueMutableLiveData

    private val minimumWidthMutableLiveData = MutableLiveData(0f)
    val minimumWidthLiveData: LiveData<Float> get() = minimumWidthMutableLiveData
    private val minimumWidth get() = minimumWidthMutableLiveData.value!!.toInt()

    private val maximumWidthMutableLiveData = MutableLiveData(1f)
    val maximumWidthLiveData: LiveData<Float> get() = maximumWidthMutableLiveData
    private val maximumWidth get() = maximumWidthMutableLiveData.value!!.toInt()

    private val widthTextMutableLiveData = MutableLiveData<String>()
    val widthTextLiveData: LiveData<String> get() = widthTextMutableLiveData

    private val isWidthShowErrorMutableLiveData = MutableLiveData<Boolean>()
    val isWidthShowErrorLiveData: LiveData<Boolean> get() = isWidthShowErrorMutableLiveData
    // endregion

    // region height
    var height: Int? = null
        private set(value) {
            field = value
            updateNextButtonState()
        }

    private val heightSliderValueMutableLiveData = MutableLiveData(1f)
    val heightSliderLiveData: LiveData<Float> get() = heightSliderValueMutableLiveData

    private val heightTextMutableLiveData = MutableLiveData<String>()
    val heightTextLiveData: LiveData<String> get() = heightTextMutableLiveData

    private val isHeightShowErrorMutableLiveData = MutableLiveData<Boolean>()
    val isHeightShowErrorLiveData: LiveData<Boolean> get() = isHeightShowErrorMutableLiveData

    private val minimumHeightMutableLiveData = MutableLiveData(0f)
    val minimumHeightLiveData: LiveData<Float> get() = minimumHeightMutableLiveData
    private val minimumHeight get() = minimumHeightMutableLiveData.value!!.toInt()

    private val maximumHeightMutableLiveData = MutableLiveData(1f)
    val maximumHeightLiveData: LiveData<Float> get() = maximumHeightMutableLiveData
    private val maximumHeight get() = maximumHeightMutableLiveData.value!!.toInt()
    // endregion

    private val isProgressShownMutableLiveData = MutableLiveData(true)
    val isProgressShownLiveData: LiveData<Boolean> get() = isProgressShownMutableLiveData

    private val isNextButtonEnabledMutableLiveData = MutableLiveData(false)
    val isNextButtonEnabledLiveData: LiveData<Boolean> get() = isNextButtonEnabledMutableLiveData

    init {
        viewModelScope.launch {
            isProgressShownMutableLiveData.value = true
            calculatorInteractor.getWindowDimensionsLimits().let { limits ->
                minimumWidthMutableLiveData.value = limits.minWidth.toFloat()
                maximumWidthMutableLiveData.value = limits.maxWidth.toFloat()
                minimumHeightMutableLiveData.value = limits.minHeight.toFloat()
                maximumHeightMutableLiveData.value = limits.maxHeight.toFloat()
                ((limits.minWidth + limits.maxWidth) / 2).let { newWidth ->
                    width = newWidth
                    isWidthShowErrorMutableLiveData.value = false
                    widthSliderValueMutableLiveData.value = newWidth.toFloat()
                    widthTextMutableLiveData.value = newWidth.toString()
                }
                ((limits.minHeight + limits.maxHeight) / 2).let { newHeight ->
                    height = newHeight
                    isHeightShowErrorMutableLiveData.value = false
                    heightSliderValueMutableLiveData.value = newHeight.toFloat()
                    heightTextMutableLiveData.value = newHeight.toString()
                }
            }
            isProgressShownMutableLiveData.value = false
        }
    }

    fun onWidthSliderValueChanged(value: Float) {
        widthSliderValueMutableLiveData.value = value
        value.toInt().let { intValue ->
            width = intValue
            isWidthShowErrorMutableLiveData.value = false
            widthTextMutableLiveData.value = intValue.toString()
        }
    }

    fun onWidthTextChanged(text: String) {
        widthTextMutableLiveData.value = text
        val width = text.toIntOrNull()
        if (width != null && width in minimumWidth..maximumWidth) {
            this.width = width
            isWidthShowErrorMutableLiveData.value = false
            widthSliderValueMutableLiveData.value = width.toFloat()
        } else {
            this.width = null
            isWidthShowErrorMutableLiveData.value = true
        }
    }

    fun onHeightSliderValueChanged(value: Float) {
        heightSliderValueMutableLiveData.value = value
        value.toInt().let { intValue ->
            height = intValue
            isHeightShowErrorMutableLiveData.value = false
            heightTextMutableLiveData.value = intValue.toString()
        }
    }

    fun onHeightTextChanged(text: String) {
        heightTextMutableLiveData.value = text
        val height = text.toIntOrNull()
        if (height != null && height in minimumHeight..maximumHeight) {
            this.height = height
            isHeightShowErrorMutableLiveData.value = false
            heightSliderValueMutableLiveData.value = height.toFloat()
        } else {
            this.height = null
            isHeightShowErrorMutableLiveData.value = true
        }
    }

    private fun updateNextButtonState() {
        isNextButtonEnabledMutableLiveData.value = width != null && height != null
    }
}