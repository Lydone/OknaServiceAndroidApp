package com.lydone.okna_service_android_app.presentation.calculator.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lydone.okna_service_android_app.domain.interactor.CalculatorInteractor
import com.lydone.okna_service_android_app.domain.model.WindowDimensionsLimits
import com.lydone.okna_service_android_app.presentation.core.State
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WindowDimensionsViewModel @Inject constructor(private val calculatorInteractor: CalculatorInteractor) :
    ViewModel() {

    private val dataMutableLiveData = MutableLiveData(
        Data(
            limits = State.Loading(),
            widthSliderValue = 0,
            widthTextValue = "",
            heightSliderValue = 0,
            heightTextValue = "",
        )
    )
    val dataLiveData: LiveData<Data> get() = dataMutableLiveData

    private var data
        get() = dataMutableLiveData.value!!
        set(value) {
            dataMutableLiveData.value = value
        }

    init {
        loadWindowDimensionsLimits()
    }

    fun onWidthSliderValueChanged(value: Float) {
        value.toInt().let { intValue ->
            data = data.copy(
                widthSliderValue = intValue,
                widthTextValue = intValue.toString(),
            )
        }
    }

    fun onWidthTextChanged(text: String) {
        (data.limits as? State.Success)?.data?.let { limits ->
            data = if (isValidTextValue(text, limits.minWidth, limits.maxWidth)) {
                data.copy(
                    widthSliderValue = text.toInt(),
                    widthTextValue = text,
                )
            } else {
                data.copy(widthTextValue = text)
            }
        }
    }

    fun onHeightSliderValueChanged(value: Float) {
        value.toInt().let { intValue ->
            data = data.copy(
                heightSliderValue = intValue,
                heightTextValue = intValue.toString(),
            )
        }
    }

    fun onHeightTextChanged(text: String) {
        (data.limits as? State.Success)?.data?.let { limits ->
            data = if (isValidTextValue(text, limits.minHeight, limits.maxHeight)) {
                data.copy(
                    heightSliderValue = text.toInt(),
                    heightTextValue = text,
                )
            } else {
                data.copy(heightTextValue = text)
            }
        }
    }

    fun onRepeatSnackbarButtonClicked() {
        loadWindowDimensionsLimits()
    }

    private fun loadWindowDimensionsLimits() {
        viewModelScope.launch {
            data = data.copy(limits = State.Loading())
            data = try {
                val limits = calculatorInteractor.getWindowDimensionsLimits()
                val width = (limits.minWidth + limits.maxWidth) / 2
                val height = (limits.minWidth + limits.maxWidth) / 2
                data.copy(
                    limits = State.Success(limits),
                    widthSliderValue = width,
                    widthTextValue = width.toString(),
                    heightSliderValue = height,
                    heightTextValue = height.toString()
                )
            } catch (e: Exception) {
                data.copy(limits = State.Error(e))
            }
        }
    }

    companion object {
        private fun isValidTextValue(text: String, minValue: Int, maxValue: Int) =
            text.toIntOrNull()?.let { it in minValue..maxValue } ?: false
    }

    data class Data(
        val limits: State<WindowDimensionsLimits>,
        val widthSliderValue: Int,
        val widthTextValue: String,
        val heightSliderValue: Int,
        val heightTextValue: String,
    ) {
        val isValidWidthText = (limits as? State.Success)?.data?.let { limits ->
            isValidTextValue(widthTextValue, limits.minWidth, limits.maxWidth)
        } ?: false

        val isValidHeightText = (limits as? State.Success)?.data?.let { limits ->
            isValidTextValue(heightTextValue, limits.minHeight, limits.maxHeight)
        } ?: false

        val isNextButtonEnabled = isValidWidthText && isValidHeightText
    }
}