package com.lydone.okna_service_android_app.presentation.calculator.fragment

import android.os.Bundle
import android.text.Editable
import android.view.View
import android.widget.Button
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.progressindicator.LinearProgressIndicator
import com.google.android.material.slider.Slider
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.lydone.okna_service_android_app.R
import com.lydone.okna_service_android_app.databinding.FragmentWindowDimensionsBinding
import com.lydone.okna_service_android_app.presentation.calculator.model.WindowDimensionsViewModel
import com.lydone.okna_service_android_app.presentation.core.AfterTextChangedWatcher
import com.lydone.okna_service_android_app.presentation.core.State
import com.lydone.okna_service_android_app.presentation.core.setTextIgnoringTextWatcher
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WindowDimensionsFragment : Fragment(R.layout.fragment_window_dimensions) {

    private val viewModel by viewModels<WindowDimensionsViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val viewBinding = FragmentWindowDimensionsBinding.bind(view)
        setupWidthSlider(viewBinding.widthSlider)
        setupWidthTextInput(viewBinding.widthTextInputEditText, viewBinding.widthTextInputLayout)
        setupHeightSlider(viewBinding.heightSlider)
        setupHeightTextInput(viewBinding.heightTextInputEditText, viewBinding.heightTextInputLayout)
        setupLinearProgressIndicator(viewBinding.linearProgressIndicator)
        setupMeasureUsingArButton(viewBinding.measureUsingAr)
        setupNextButton(viewBinding.nextButton)
        setupErrorSnackbar()
    }

    private fun setupWidthSlider(slider: Slider) {
        slider.addOnChangeListener { _, value, fromUser ->
            if (fromUser) {
                viewModel.onWidthSliderValueChanged(value)
            }
        }
        viewModel.dataLiveData.observe(viewLifecycleOwner) { data ->
            slider.isVisible = (data.limits is State.Success)
            (data.limits as? State.Success)?.data?.let { limits ->
                slider.valueFrom = limits.minWidth.toFloat()
                slider.valueTo = limits.maxWidth.toFloat()
                slider.value = data.widthSliderValue.toFloat()
            }
        }
    }

    private fun setupHeightSlider(slider: Slider) {
        slider.addOnChangeListener { _, value, fromUser ->
            if (fromUser) {
                viewModel.onHeightSliderValueChanged(value)
            }
        }
        viewModel.dataLiveData.observe(viewLifecycleOwner) { data ->
            slider.isVisible = (data.limits is State.Success)
            (data.limits as? State.Success)?.data?.let { limits ->
                slider.valueFrom = limits.minHeight.toFloat()
                slider.valueTo = limits.maxHeight.toFloat()
                slider.value = data.heightSliderValue.toFloat()
            }
        }
    }

    private fun setupWidthTextInput(editText: TextInputEditText, layout: TextInputLayout) {
        val widthTextWatcher = object : AfterTextChangedWatcher {
            override fun afterTextChanged(s: Editable) {
                viewModel.onWidthTextChanged(s.toString())
            }
        }
        editText.addTextChangedListener(widthTextWatcher)
        viewModel.dataLiveData.observe(viewLifecycleOwner) { data ->
            layout.isVisible = data.limits is State.Success
            editText.setTextIgnoringTextWatcher(data.widthTextValue, widthTextWatcher)
            layout.error = if (data.isValidWidthText) null else "Неверно"
        }
    }

    private fun setupHeightTextInput(editText: TextInputEditText, layout: TextInputLayout) {
        val heightTextWatcher = object : AfterTextChangedWatcher {
            override fun afterTextChanged(s: Editable) {
                viewModel.onHeightTextChanged(s.toString())
            }
        }
        editText.addTextChangedListener(heightTextWatcher)
        viewModel.dataLiveData.observe(viewLifecycleOwner) { data ->
            layout.isVisible = data.limits is State.Success
            editText.setTextIgnoringTextWatcher(data.heightTextValue, heightTextWatcher)
            layout.error = if (data.isValidHeightText) null else "Неверно"
        }
    }

    private fun setupLinearProgressIndicator(indicator: LinearProgressIndicator) {
        //TODO Убрать этот параметр в разметку как только гугл пофиксит
        indicator.showAnimationBehavior = LinearProgressIndicator.SHOW_INWARD
        viewModel.dataLiveData.observe(viewLifecycleOwner) { data ->
            if (data.limits is State.Loading) indicator.show() else indicator.hide()
        }
    }

    private fun setupNextButton(button: Button) {
        button.setOnClickListener {
            viewModel.dataLiveData.value!!.let { data ->
                findNavController().navigate(
                    WindowDimensionsFragmentDirections.actionWindowDimensionsFragmentToOptions(
                        width = requireNotNull(data.widthSliderValue),
                        height = requireNotNull(data.heightSliderValue)
                    )
                )
            }

        }
        viewModel.dataLiveData.observe(viewLifecycleOwner) { button.isEnabled = it.isNextButtonEnabled }
    }

    private fun setupMeasureUsingArButton(button: Button) {
        viewModel.dataLiveData.observe(viewLifecycleOwner) {
            button.isVisible = it.limits is State.Success && it.isArAvailable
        }
    }

    private fun setupErrorSnackbar() {
        viewModel.dataLiveData.observe(viewLifecycleOwner) { data ->
            if (data.limits is State.Error) {
                Snackbar.make(requireView(), R.string.error_has_occurred, Snackbar.LENGTH_INDEFINITE)
                    .setAction(R.string.repeat) { viewModel.onRepeatSnackbarButtonClicked() }.show()
            }
        }
    }
}