package com.lydone.okna_service_android_app.presentation.calculator.fragment

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.IdRes
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.Group
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
            setupContentGroup(contentGroup)
            setupPriceCircularProgressIndicator(priceCircularProgressIndicator)
            setupMaterialPicker(materialTextView, materialConstraintLayout)
            setupWindowTypeChipGroup(windowTypeChipGroup)
            setupSashTypesRecyclerView(sashesRecyclerView)
            setupGlassUnitChipGroup(glassUnitChipGroup)
            setupOptionsPicker(
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

            viewModel.isErrorShownLiveData.observe(viewLifecycleOwner) { errorFrameLayout.isVisible = it }
            setupRepeatButton(repeatButton)
        }
    }

    private fun setupOptionsPicker(
        windowsillCheckBox: CheckBox,
        ebbCheckBox: CheckBox,
        slopeCheckBox: CheckBox,
        laminationCheckBox: CheckBox,
        mosquitoNetCheckBox: CheckBox
    ) {
        setupWindowsillCheckBox(windowsillCheckBox)
        setupEbbCheckBox(ebbCheckBox)
        setupSlopeCheckBox(slopeCheckBox)
        setupLaminationCheckBox(laminationCheckBox)
        setupMosquitoNetCheckBox(mosquitoNetCheckBox)
    }

    private fun setupLinearProgressIndicator(indicator: LinearProgressIndicator) {
        indicator.showAnimationBehavior = CircularProgressIndicator.SHOW_INWARD
        viewModel.windowLiveData.observe(viewLifecycleOwner) { window ->
            if (window != null) indicator.hide() else indicator.show()
        }
    }

    private fun setupContentGroup(group: Group) {
        viewModel.windowLiveData.observe(viewLifecycleOwner) { group.isVisible = it != null }
    }

    private fun setupPriceCircularProgressIndicator(indicator: CircularProgressIndicator) {
        indicator.showAnimationBehavior = CircularProgressIndicator.SHOW_INWARD
        viewModel.priceLiveData.observe(viewLifecycleOwner) { state ->
            if (state is State.Loading) indicator.show() else indicator.hide()
        }
    }

    private fun setupWindowsillCheckBox(checkBox: CheckBox) {
        checkBox.setOnCheckedChangeListener { _, isChecked -> viewModel.onWindowsillCheckChanged(isChecked) }
        viewModel.windowLiveData.observe(viewLifecycleOwner) { window ->
            window?.let { checkBox.isChecked = it.isWindowsillIncluded }
        }
    }

    private fun setupEbbCheckBox(checkBox: CheckBox) {
        checkBox.setOnCheckedChangeListener { _, isChecked -> viewModel.onEbbCheckChanged(isChecked) }
        viewModel.windowLiveData.observe(viewLifecycleOwner) { window ->
            window?.let { checkBox.isChecked = it.isEbbIncluded }
        }
    }

    private fun setupSlopeCheckBox(checkBox: CheckBox) {
        checkBox.setOnCheckedChangeListener { _, isChecked -> viewModel.onSlopeCheckChanged(isChecked) }
        viewModel.windowLiveData.observe(viewLifecycleOwner) { window ->
            window?.let { checkBox.isChecked = it.isSlopeIncluded }
        }
    }

    private fun setupLaminationCheckBox(checkBox: CheckBox) {
        checkBox.setOnCheckedChangeListener { _, isChecked -> viewModel.onLaminationCheckChanged(isChecked) }
        viewModel.windowLiveData.observe(viewLifecycleOwner) { window ->
            window?.let { checkBox.isChecked = it.isLaminationIncluded }
        }
    }

    private fun setupMosquitoNetCheckBox(checkBox: CheckBox) {
        checkBox.setOnCheckedChangeListener { _, isChecked -> viewModel.onMosquitoNetCheckChanged(isChecked) }
        viewModel.windowLiveData.observe(viewLifecycleOwner) { window ->
            window?.let { checkBox.isChecked = it.isMosquitoNetIncluded }
        }
    }

    private fun setupGlassUnitChipGroup(chipGroup: ChipGroup) {
        viewModel.windowLiveData.observe(viewLifecycleOwner) { window ->
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
            window?.let { textView.setText(MaterialTypeToStringResConverter.convertToTitleString(it.materialType)) }
        }
    }

    private fun setupSashTypesRecyclerView(recyclerView: RecyclerView) {
        val adapter = SashTypeAdapter { position, newType -> viewModel.onSashTypeChanged(position, newType) }
        recyclerView.adapter = adapter
        viewModel.windowLiveData.observe(viewLifecycleOwner) { window ->
            window?.let { adapter.checkedSashes = it.sashes }
        }
    }

    private fun setupWindowTypeChipGroup(chipGroup: ChipGroup) {
        chipGroup.setOnCheckedChangeListener { _, checkedId ->
            viewModel.onWindowTypeChanged(mapChipIdToWindowType(checkedId))
        }
        viewModel.matchingWindowTypesLiveData.observe(viewLifecycleOwner) { types ->
            for (chip in chipGroup.children) {
                chip.isVisible = mapChipIdToWindowType(chip.id) in types
            }
        }
        viewModel.windowLiveData.observe(viewLifecycleOwner) { window ->
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
            window?.let { imageView.setImageResource(WindowTypeToDrawableResConverter.convert(it.windowType)) }
        }
    }

    private fun setupPriceTextView(textView: TextView) {
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
        viewModel.windowLiveData.observe(viewLifecycleOwner) { button.isEnabled = it != null }
        button.setOnClickListener { viewModel.onAddToCartButtonClicked() }
    }

    private fun setupUpdateInCartButton(button: Button) {
        viewModel.modeLiveData.observe(viewLifecycleOwner) { mode ->
            button.isVisible = mode == WindowConstructorViewModel.Mode.UPDATE
        }
        viewModel.windowLiveData.observe(viewLifecycleOwner) { button.isEnabled = it != null }
        button.setOnClickListener { viewModel.onUpdateInCartButtonClicked() }
    }

    private fun setupNavigation() {
        viewModel.navigateToCartLiveData.observe(viewLifecycleOwner) {
            findNavController().navigate(WindowConstructorFragmentDirections.actionCalculatorFragmentToCart())
        }
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