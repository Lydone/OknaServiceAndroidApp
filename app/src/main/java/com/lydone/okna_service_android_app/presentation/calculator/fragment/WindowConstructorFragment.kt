package com.lydone.okna_service_android_app.presentation.calculator.fragment

import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.annotation.IdRes
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.children
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.chip.ChipGroup
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
            setupMaterialPicker(materialTextView, materialConstraintLayout)
            setupWindowTypePicker(windowTypeChipGroup, windowTypeScrollView, windowTypeTextView)
            setupSashTypesPicker(sashesRecyclerView, sashesTextView)
            setupGlassUnitPicker(glassUnitChipGroup, glassUnitTextView)
            setupOptionsPicker(
                windowsillCheckBox,
                ebbCheckBox,
                slopeCheckBox,
                laminationCheckBox,
                mosquitoNetCheckBox,
                optionsTextView
            )

            setupPriceLayout(priceTextView, priceConstraintLayout)
            setupUpdateInCartButton(updateInCartButton)
            setupAddToCartButton(addToCartButton)
            setupNavigation()
        }
    }

    private fun setupOptionsPicker(
        windowsillCheckBox: CheckBox,
        ebbCheckBox: CheckBox,
        slopeCheckBox: CheckBox,
        laminationCheckBox: CheckBox,
        mosquitoNetCheckBox: CheckBox,
        textView: TextView
    ) {
        setupWindowsillCheckBox(windowsillCheckBox)
        setupEbbCheckBox(ebbCheckBox)
        setupSlopeCheckBox(slopeCheckBox)
        setupLaminationCheckBox(laminationCheckBox)
        setupMosquitoNetCheckBox(mosquitoNetCheckBox)
        setupOptionsTextView(textView)
    }

    private fun setupLinearProgressIndicator(indicator: LinearProgressIndicator) {
        viewModel.windowLiveData.observe(viewLifecycleOwner) { window ->
            if (window != null && viewModel.priceLiveData.value is State.Success) {
                indicator.hide()
            } else {
                indicator.show()
            }
        }
        viewModel.priceLiveData.observe(viewLifecycleOwner) { state ->
            if (viewModel.windowLiveData.value != null && state is State.Success) {
                indicator.hide()
            } else {
                indicator.show()
            }
        }
    }

    private fun setupWindowsillCheckBox(checkBox: CheckBox) {
        checkBox.setOnCheckedChangeListener { _, isChecked -> viewModel.onWindowsillCheckChanged(isChecked) }
        viewModel.windowLiveData.observe(viewLifecycleOwner) { window ->
            checkBox.isVisible = window != null
            window?.let { checkBox.isChecked = it.isWindowsillIncluded }
        }
    }

    private fun setupEbbCheckBox(checkBox: CheckBox) {
        checkBox.setOnCheckedChangeListener { _, isChecked -> viewModel.onEbbCheckChanged(isChecked) }
        viewModel.windowLiveData.observe(viewLifecycleOwner) { window ->
            checkBox.isVisible = window != null
            window?.let { checkBox.isChecked = it.isEbbIncluded }
        }
    }

    private fun setupSlopeCheckBox(checkBox: CheckBox) {
        checkBox.setOnCheckedChangeListener { _, isChecked -> viewModel.onSlopeCheckChanged(isChecked) }
        viewModel.windowLiveData.observe(viewLifecycleOwner) { window ->
            checkBox.isVisible = window != null
            window?.let { checkBox.isChecked = it.isSlopeIncluded }
        }
    }

    private fun setupLaminationCheckBox(checkBox: CheckBox) {
        checkBox.setOnCheckedChangeListener { _, isChecked -> viewModel.onLaminationCheckChanged(isChecked) }
        viewModel.windowLiveData.observe(viewLifecycleOwner) { window ->
            checkBox.isVisible = window != null
            window?.let { checkBox.isChecked = it.isLaminationIncluded }
        }
    }

    private fun setupMosquitoNetCheckBox(checkBox: CheckBox) {
        checkBox.setOnCheckedChangeListener { _, isChecked -> viewModel.onMosquitoNetCheckChanged(isChecked) }
        viewModel.windowLiveData.observe(viewLifecycleOwner) { window ->
            checkBox.isVisible = window != null
            window?.let { checkBox.isChecked = it.isMosquitoNetIncluded }
        }
    }

    private fun setupOptionsTextView(textView: TextView) {
        viewModel.windowLiveData.observe(viewLifecycleOwner) { textView.isVisible = it != null }
    }

    private fun setupGlassUnitPicker(chipGroup: ChipGroup, textView: TextView) {
        viewModel.windowLiveData.observe(viewLifecycleOwner) { window ->
            chipGroup.isVisible = window != null
            textView.isVisible = window != null
            window?.let { chipGroup.check(ChipIdToGlassUnitTypeConverter.convertBack(it.glassUnitType)) }
        }
        chipGroup.setOnCheckedChangeListener { _, checkedId ->
            viewModel.onGlassUnitTypeChanged(ChipIdToGlassUnitTypeConverter.convert(checkedId))
        }
    }

    private fun setupMaterialPicker(textView: TextView, layout: ConstraintLayout) {
        layout.setOnClickListener {
            findNavController().navigate(WindowConstructorFragmentDirections.actionCalculatorFragmentToSelectMaterialTypeBottomSheet())
        }
        viewModel.windowLiveData.observe(viewLifecycleOwner) { window ->
            layout.isVisible = window != null
            window?.let { textView.setText(MaterialTypeToStringResConverter.convertToTitleString(it.materialType)) }
        }
    }

    private fun setupSashTypesPicker(recyclerView: RecyclerView, textView: TextView) {
        val adapter = SashTypeAdapter { position, newType -> viewModel.onSashTypeChanged(position, newType) }
        recyclerView.adapter = adapter
        viewModel.windowLiveData.observe(viewLifecycleOwner) { window ->
            textView.isVisible = window != null
            recyclerView.isVisible = window != null
            window?.let { adapter.checkedSashes = it.sashes }
        }
    }

    private fun setupWindowTypePicker(chipGroup: ChipGroup, scrollView: HorizontalScrollView, textView: TextView) {
        chipGroup.setOnCheckedChangeListener { _, checkedId ->
            viewModel.onWindowTypeChanged(mapChipIdToWindowType(checkedId))
        }
        viewModel.matchingWindowTypesLiveData.observe(viewLifecycleOwner) { types ->
            for (chip in chipGroup.children) {
                chip.isVisible = mapChipIdToWindowType(chip.id) in types
            }
        }
        viewModel.windowLiveData.observe(viewLifecycleOwner) { window ->
            scrollView.isVisible = window != null
            textView.isVisible = window != null
            if (window != null) {
                chipGroup.check(
                    when (window.windowType) {
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
        viewModel.windowLiveData.observe(viewLifecycleOwner) { window ->
            imageView.isVisible = window != null
            window?.let { imageView.setImageResource(WindowTypeToDrawableResConverter.convert(it.windowType)) }
        }
    }

    private fun setupPriceLayout(textView: TextView, layout: ConstraintLayout) {
        viewModel.windowLiveData.observe(viewLifecycleOwner) { layout.isVisible = it != null }
        viewModel.priceLiveData.observe(viewLifecycleOwner) { state ->
            textView.visibility = if (state is State.Success) View.VISIBLE else View.INVISIBLE
            if (state is State.Success) {
                textView.text = getString(R.string.ruble_placeholder, state.data)
            }
        }
    }

    private fun setupAddToCartButton(button: Button) {
        viewModel.modeLiveData.observe(viewLifecycleOwner) { mode ->
            button.isVisible = mode == WindowConstructorViewModel.Mode.ADD
        }
        button.setOnClickListener { viewModel.onAddToCartButtonClicked() }
    }

    private fun setupUpdateInCartButton(button: Button) {
        viewModel.modeLiveData.observe(viewLifecycleOwner) { mode ->
            button.isVisible = mode == WindowConstructorViewModel.Mode.UPDATE
        }
        button.setOnClickListener { viewModel.onUpdateInCartButtonClicked() }
    }

    private fun setupNavigation() {
        viewModel.navigateToCartLiveData.observe(viewLifecycleOwner) {
            findNavController().navigate(WindowConstructorFragmentDirections.actionCalculatorFragmentToCartFragment())
        }
    }
}