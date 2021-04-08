package com.lydone.okna_service_android_app.presentation.calculator.fragment

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.IdRes
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.children
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.chip.ChipGroup
import com.google.android.material.progressindicator.CircularProgressIndicator
import com.google.android.material.progressindicator.LinearProgressIndicator
import com.lydone.okna_service_android_app.R
import com.lydone.okna_service_android_app.databinding.FragmentWindowConstructorBinding
import com.lydone.okna_service_android_app.domain.model.WindowType
import com.lydone.okna_service_android_app.presentation.calculator.converter.ChipIdToGlassUnitTypeConverter
import com.lydone.okna_service_android_app.presentation.calculator.model.WindowConstructorViewModel
import com.lydone.okna_service_android_app.presentation.calculator.recycler.sash.SashTypeAdapter
import com.lydone.okna_service_android_app.presentation.common.MaterialTypeToStringResConverter
import com.lydone.okna_service_android_app.presentation.common.WindowTypeToDrawableResConverter
import com.lydone.okna_service_android_app.presentation.core.State
import dagger.hilt.android.AndroidEntryPoint

@Suppress("WHEN_ENUM_CAN_BE_NULL_IN_JAVA")
@AndroidEntryPoint
class WindowConstructorFragment : Fragment(R.layout.fragment_window_constructor) {

    private val viewModel: WindowConstructorViewModel by hiltNavGraphViewModels(R.id.graph_window_constructor)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(WindowConstructorFragmentArgs.fromBundle(requireArguments())) {
            if (id != -1) {
                viewModel.onFragmentAttached(id)
            } else {
                viewModel.onFragmentAttached(width, height)
            }
        }
        with(FragmentWindowConstructorBinding.bind(view)) {
            setupWindowImageView(imageView)
            setupLinearProgressIndicator(linearProgressIndicator)
            setupPriceCircularProgressIndicator(priceCircularProgressIndicator)
            setupMaterialPicker(materialTextView, materialConstraintLayout)
            setupWindowTypeSection(windowTypeTextView, windowTypeChipGroup)
            setupSashTypesSection(sashesTextView, sashesRecyclerView)
            setupGlassUnitTypeSection(glassUnitTextView, glassUnitChipGroup)
            setupOptionsSection(
                optionsTextView,
                windowsillCheckBox,
                ebbCheckBox,
                slopeCheckBox,
                laminationCheckBox,
                mosquitoNetCheckBox
            )

            setupPriceTextView(priceTextView)
            setupUpdateInCartButton(updateInCartButton)
            setupAddToCartButton(addToCartButton)
            setupNavigation()
            setupErrorLayout(errorConstraintLayout)
            setupRepeatButton(repeatButton)
        }
    }

    private fun setupOptionsSection(
        textView: TextView,
        windowsillCheckBox: CheckBox,
        ebbCheckBox: CheckBox,
        slopeCheckBox: CheckBox,
        laminationCheckBox: CheckBox,
        mosquitoNetCheckBox: CheckBox
    ) {
        setupOptionsTitleTextView(textView)
        setupWindowsillCheckBox(windowsillCheckBox)
        setupEbbCheckBox(ebbCheckBox)
        setupSlopeCheckBox(slopeCheckBox)
        setupLaminationCheckBox(laminationCheckBox)
        setupMosquitoNetCheckBox(mosquitoNetCheckBox)
    }

    private fun setupLinearProgressIndicator(indicator: LinearProgressIndicator) {
        indicator.showAnimationBehavior = CircularProgressIndicator.SHOW_INWARD
        viewModel.dataStateLiveData.observe(viewLifecycleOwner) { state ->
            indicator.isVisible = state is State.Loading
        }
    }

    private fun setupPriceCircularProgressIndicator(indicator: CircularProgressIndicator) {
        indicator.showAnimationBehavior = CircularProgressIndicator.SHOW_INWARD
        viewModel.dataStateLiveData.observe(viewLifecycleOwner) { state ->
            if (state is State.Success && state.data.priceState is State.Loading) indicator.show() else indicator.hide()
        }
    }

    private fun setupOptionsTitleTextView(textView: TextView) {
        viewModel.dataStateLiveData.observe(viewLifecycleOwner) { textView.isVisible = it is State.Success }
    }

    private fun setupWindowsillCheckBox(checkBox: CheckBox) {
        checkBox.setOnCheckedChangeListener { _, isChecked -> viewModel.onWindowsillCheckChanged(isChecked) }
        viewModel.dataStateLiveData.observe(viewLifecycleOwner) { state ->
            checkBox.isVisible = state is State.Success
            if (state is State.Success) {
                checkBox.isChecked = state.data.window.isWindowsillIncluded
            }
        }
    }

    private fun setupEbbCheckBox(checkBox: CheckBox) {
        checkBox.setOnCheckedChangeListener { _, isChecked -> viewModel.onEbbCheckChanged(isChecked) }
        viewModel.dataStateLiveData.observe(viewLifecycleOwner) { state ->
            checkBox.isVisible = state is State.Success
            if (state is State.Success) {
                checkBox.isChecked = state.data.window.isEbbIncluded
            }
        }
    }

    private fun setupSlopeCheckBox(checkBox: CheckBox) {
        checkBox.setOnCheckedChangeListener { _, isChecked -> viewModel.onSlopeCheckChanged(isChecked) }
        viewModel.dataStateLiveData.observe(viewLifecycleOwner) { state ->
            checkBox.isVisible = state is State.Success
            if (state is State.Success) {
                checkBox.isChecked = state.data.window.isSlopeIncluded
            }
        }
    }

    private fun setupLaminationCheckBox(checkBox: CheckBox) {
        checkBox.setOnCheckedChangeListener { _, isChecked -> viewModel.onLaminationCheckChanged(isChecked) }
        viewModel.dataStateLiveData.observe(viewLifecycleOwner) { state ->
            checkBox.isVisible = state is State.Success
            if (state is State.Success) {
                checkBox.isChecked = state.data.window.isLaminationIncluded
            }
        }
    }

    private fun setupMosquitoNetCheckBox(checkBox: CheckBox) {
        checkBox.setOnCheckedChangeListener { _, isChecked -> viewModel.onMosquitoNetCheckChanged(isChecked) }
        viewModel.dataStateLiveData.observe(viewLifecycleOwner) { state ->
            checkBox.isVisible = state is State.Success
            if (state is State.Success) {
                checkBox.isChecked = state.data.window.isMosquitoNetIncluded
            }
        }
    }

    private fun setupGlassUnitTypeSection(textView: TextView, chipGroup: ChipGroup) {
        viewModel.dataStateLiveData.observe(viewLifecycleOwner) { state ->
            chipGroup.isVisible = state is State.Success
            textView.isVisible = state is State.Success
            if (state is State.Success) {
                chipGroup.check(ChipIdToGlassUnitTypeConverter.convertBack(state.data.window.glassUnitType))
            }
        }
        chipGroup.setOnCheckedChangeListener { _, checkedId ->
            viewModel.onGlassUnitTypeChanged(ChipIdToGlassUnitTypeConverter.convert(checkedId))
        }
    }

    private fun setupMaterialPicker(textView: TextView, layout: ConstraintLayout) {
        layout.setOnClickListener {
            findNavController().navigate(WindowConstructorFragmentDirections.actionCalculatorFragmentToSelectMaterialTypeBottomSheet())
        }
        viewModel.dataStateLiveData.observe(viewLifecycleOwner) { state ->
            layout.isVisible = state is State.Success
            if (state is State.Success) {
                textView.setText(MaterialTypeToStringResConverter.convertToTitleString(state.data.window.materialType))
            }
        }
    }

    private fun setupSashTypesSection(textView: TextView, recyclerView: RecyclerView) {
        val adapter = SashTypeAdapter { position, newType -> viewModel.onSashTypeChanged(position, newType) }
        recyclerView.adapter = adapter
        viewModel.dataStateLiveData.observe(viewLifecycleOwner) { state ->
            recyclerView.isVisible = state is State.Success
            textView.isVisible = state is State.Success
            if (state is State.Success) {
                adapter.checkedSashes = state.data.window.sashes
            }
        }
    }

    private fun setupWindowTypeSection(textView: TextView, chipGroup: ChipGroup) {
        chipGroup.setOnCheckedChangeListener { _, checkedId ->
            viewModel.onWindowTypeChanged(mapChipIdToWindowType(checkedId))
        }
        viewModel.dataStateLiveData.observe(viewLifecycleOwner) { state ->
            textView.isVisible = state is State.Success
            chipGroup.isVisible = state is State.Success
            (state as? State.Success)?.data?.let { data ->
                for (chip in chipGroup.children) {
                    chip.isVisible = mapChipIdToWindowType(chip.id) in data.matchingWindowTypes
                }
                chipGroup.check(
                    when (data.window.windowType) {
                        WindowType.ONE_SASH -> R.id.one_sash_chip
                        WindowType.TWO_SASHES -> R.id.two_sashes_chip
                        WindowType.THREE_SASHES -> R.id.three_sashes_chip
                    }
                )
            }
        }
    }

    private fun mapChipIdToWindowType(@IdRes id: Int) = when (id) {
        R.id.one_sash_chip -> WindowType.ONE_SASH
        R.id.two_sashes_chip -> WindowType.TWO_SASHES
        R.id.three_sashes_chip -> WindowType.THREE_SASHES
        else -> throw IllegalArgumentException("Incorrect chip id: $id")
    }

    private fun setupWindowImageView(imageView: ImageView) {
        viewModel.dataStateLiveData.observe(viewLifecycleOwner) { state ->
            imageView.isVisible = state is State.Success
            (state as? State.Success)?.data?.window?.windowType?.let { windowType ->
                imageView.setImageResource(WindowTypeToDrawableResConverter.convert(windowType))
            }
        }
    }

    private fun setupPriceTextView(textView: TextView) {
        viewModel.dataStateLiveData.observe(viewLifecycleOwner) { state ->
            if (state is State.Success) {
                when (val priceState = state.data.priceState) {
                    is State.Error -> {
                        textView.isVisible = true
                        textView.text = getString(R.string.error_has_occurred)
                    }
                    is State.Loading -> {
                        textView.visibility = View.INVISIBLE
                    }
                    is State.Success -> {
                        textView.isVisible = true
                        textView.text = getString(R.string.ruble_placeholder, priceState.data)
                    }
                }
            } else {
                textView.isVisible = false
            }
        }
    }

    private fun setupAddToCartButton(button: Button) {
        button.isVisible = viewModel.mode == WindowConstructorViewModel.Mode.ADD
        viewModel.dataStateLiveData.observe(viewLifecycleOwner) { button.isEnabled = it is State.Success }
        button.setOnClickListener { viewModel.onAddToCartButtonClicked() }
    }

    private fun setupUpdateInCartButton(button: Button) {
        button.isVisible = viewModel.mode == WindowConstructorViewModel.Mode.UPDATE
        viewModel.dataStateLiveData.observe(viewLifecycleOwner) { button.isEnabled = it is State.Success }
        button.setOnClickListener { viewModel.onUpdateInCartButtonClicked() }
    }

    private fun setupErrorLayout(constraintLayout: ConstraintLayout) {
        viewModel.dataStateLiveData.observe(viewLifecycleOwner) { constraintLayout.isVisible = it is State.Error }
    }

    private fun setupNavigation() {
        viewModel.navDirectionsLiveData.observe(viewLifecycleOwner) { findNavController().navigate(it) }
    }

    private fun setupRepeatButton(button: Button) {
        button.setOnClickListener {
            with(WindowConstructorFragmentArgs.fromBundle(requireArguments())) {
                if (id != -1) {
                    viewModel.onFragmentAttached(id)
                } else {
                    viewModel.onFragmentAttached(width, height)
                }
            }
        }
    }
}