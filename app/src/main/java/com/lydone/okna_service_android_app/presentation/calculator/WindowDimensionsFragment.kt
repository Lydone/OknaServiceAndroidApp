package com.lydone.okna_service_android_app.presentation.calculator

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.slider.Slider
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.lydone.okna_service_android_app.R
import com.lydone.okna_service_android_app.databinding.FragmentWindowDimensionsBinding
import com.lydone.okna_service_android_app.presentation.calculator.model.WindowDimensionsViewModel
import com.lydone.okna_service_android_app.presentation.core.AfterTextChangedWatcher
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
        setupProgressBar(viewBinding.progressBar)
        setupMeasureUsingArButton(viewBinding.measureUsingAr)
        setupNextButton(viewBinding.nextButton)
    }

    private fun setupWidthSlider(slider: Slider) {
        slider.addOnChangeListener { _, value, fromUser ->
            if (fromUser) {
                viewModel.onWidthSliderValueChanged(value)
            }
        }
        viewModel.minimumWidthLiveData.observe(viewLifecycleOwner) { slider.valueFrom = it.toFloat() }
        viewModel.maximumWidthLiveData.observe(viewLifecycleOwner) { slider.valueTo = it.toFloat() }
        viewModel.widthSliderValueLiveData.observe(viewLifecycleOwner) { slider.value = it }
        viewModel.isProgressShownLiveData.observe(viewLifecycleOwner) { slider.isVisible = !it }
    }

    private fun setupHeightSlider(slider: Slider) {
        slider.addOnChangeListener { _, value, fromUser ->
            if (fromUser) {
                viewModel.onHeightSliderValueChanged(value)
            }
        }
        viewModel.minimumHeightLiveData.observe(viewLifecycleOwner) { slider.valueFrom = it.toFloat() }
        viewModel.maximumHeightLiveData.observe(viewLifecycleOwner) { slider.valueTo = it.toFloat() }
        viewModel.heightSliderLiveData.observe(viewLifecycleOwner) { slider.value = it }
        viewModel.isProgressShownLiveData.observe(viewLifecycleOwner) { slider.isVisible = !it }
    }

    private fun setupWidthTextInput(editText: TextInputEditText, layout: TextInputLayout) {
        val widthTextWatcher = object : AfterTextChangedWatcher {
            override fun afterTextChanged(s: Editable) {
                viewModel.onWidthTextChanged(s.toString())
            }
        }
        editText.addTextChangedListener(widthTextWatcher)
        viewModel.widthTextLiveData.observe(viewLifecycleOwner) { width ->
            editText.setTextIgnoringTextWatcher(width, widthTextWatcher)
        }
        viewModel.isWidthShowErrorLiveData.observe(viewLifecycleOwner) { isShowError ->
            layout.error = if (isShowError) {
                "Неверно"
            } else {
                null
            }
        }
        viewModel.isProgressShownLiveData.observe(viewLifecycleOwner) { layout.isVisible = !it }
    }

    private fun setupHeightTextInput(editText: TextInputEditText, layout: TextInputLayout) {
        val heightTextWatcher = object : AfterTextChangedWatcher {
            override fun afterTextChanged(s: Editable) {
                viewModel.onHeightTextChanged(s.toString())
            }
        }
        editText.addTextChangedListener(heightTextWatcher)
        viewModel.heightTextLiveData.observe(viewLifecycleOwner) { height ->
            editText.setTextIgnoringTextWatcher(height, heightTextWatcher)
        }
        viewModel.isHeightShowErrorLiveData.observe(viewLifecycleOwner) { isShowError ->
            layout.error = if (isShowError) {
                "Неверно"
            } else {
                null
            }
        }
        viewModel.isProgressShownLiveData.observe(viewLifecycleOwner) { layout.isVisible = !it }
    }

    private fun setupProgressBar(progressBar: ProgressBar) {
        viewModel.isProgressShownLiveData.observe(viewLifecycleOwner) { progressBar.isVisible = it }
    }

    private fun setupNextButton(button: Button) {
        button.setOnClickListener {
            findNavController().navigate(
                WindowDimensionsFragmentDirections.actionWindowDimensionsFragmentToOptions(
                    requireNotNull(viewModel.width), requireNotNull(viewModel.height)
                )
            )
        }
        viewModel.isNextButtonEnabledLiveData.observe(viewLifecycleOwner) { button.isEnabled = it }
    }

    private fun setupMeasureUsingArButton(button: Button) {
        viewModel.isProgressShownLiveData.observe(viewLifecycleOwner) { button.isVisible = !it }
    }

    private fun TextInputEditText.setTextIgnoringTextWatcher(newText: String, watcher: TextWatcher) {
        removeTextChangedListener(watcher)
        text?.clear()
        text?.append(newText)
        addTextChangedListener(watcher)
    }
}