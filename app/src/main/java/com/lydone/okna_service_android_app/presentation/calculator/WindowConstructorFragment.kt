package com.lydone.okna_service_android_app.presentation.calculator

import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.annotation.IdRes
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.children
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.chip.ChipGroup
import com.lydone.okna_service_android_app.R
import com.lydone.okna_service_android_app.databinding.FragmentWindowConstructorBinding
import com.lydone.okna_service_android_app.domain.calculator.model.WindowModel
import com.lydone.okna_service_android_app.domain.calculator.model.WindowType
import com.lydone.okna_service_android_app.presentation.calculator.converter.ChipIdToGlassUnitTypeConverter
import com.lydone.okna_service_android_app.presentation.calculator.converter.ChipIdToHouseTypeConverter
import com.lydone.okna_service_android_app.presentation.calculator.converter.MaterialTypeToStringResConverter
import com.lydone.okna_service_android_app.presentation.calculator.model.WindowConstructorViewModel
import com.lydone.okna_service_android_app.presentation.calculator.sash_type_recycler_view.SashTypeAdapter
import com.lydone.okna_service_android_app.presentation.core.State
import dagger.hilt.android.AndroidEntryPoint

@Suppress("WHEN_ENUM_CAN_BE_NULL_IN_JAVA")
@AndroidEntryPoint
class WindowConstructorFragment : Fragment(R.layout.fragment_window_constructor) {

    private val viewModel: WindowConstructorViewModel by navGraphViewModels(R.id.windowConstructor) {
        defaultViewModelProviderFactory
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(WindowConstructorFragmentArgs.fromBundle(requireArguments())) {
            viewModel.onFragmentAttached(WindowModel(width, height))
        }
        with(FragmentWindowConstructorBinding.bind(view)) {
            setupWindowImageView(imageView)
            setupMainProgressBar(mainProgressBar)
            setupMaterialPicker(materialTextView, materialConstraintLayout)
            setupWindowTypePicker(windowTypeChipGroup, windowTypeScrollView, windowTypeTextView)
            setupSashTypesPicker(sashesRecyclerView, sashesTextView)
            setupGlassUnitPicker(glassUnitChipGroup, glassUnitTextView)
            setupHouseTypePicker(houseTypeChipGroup, houseTypeTextView)
            setupOptionsPicker(
                windowsillCheckBox,
                ebbCheckBox,
                slopeCheckBox,
                laminationCheckBox,
                mosquitoNetCheckBox,
                optionsTextView
            )
            setupPriceLayout(priceTextView, priceConstraintLayout, priceProgressBar)
            setupAddToCartButton(addToCartButton)
        }
    }

    private fun setupMainProgressBar(progressBar: ProgressBar) {
        viewModel.isMainProgressShownLiveData.observe(viewLifecycleOwner) { progressBar.isVisible = it }
    }

    private fun setupOptionsPicker(
        windowsillCheckBox: CheckBox,
        ebbCheckBox: CheckBox,
        slopeCheckBox: CheckBox,
        laminationCheckBox: CheckBox,
        mosquitoNetCheckBox: CheckBox,
        textView: TextView
    ) {
        viewModel.windowModelLiveData.observe(viewLifecycleOwner) { model ->
            with(model) {
                windowsillCheckBox.isChecked = isWindowsillSelected
                ebbCheckBox.isChecked = isEbbSelected
                slopeCheckBox.isChecked = isSlopeSelected
                laminationCheckBox.isChecked = isLaminationSelected
                mosquitoNetCheckBox.isChecked = isMosquitoNetSelected
            }
        }
        windowsillCheckBox.setOnCheckedChangeListener { _, isChecked -> viewModel.onWindowsillCheckChanged(isChecked) }
        ebbCheckBox.setOnCheckedChangeListener { _, isChecked -> viewModel.onEbbCheckChanged(isChecked) }
        slopeCheckBox.setOnCheckedChangeListener { _, isChecked -> viewModel.onSlopeCheckChanged(isChecked) }
        laminationCheckBox.setOnCheckedChangeListener { _, isChecked -> viewModel.onLaminationCheckChanged(isChecked) }
        mosquitoNetCheckBox.setOnCheckedChangeListener { _, isChecked -> viewModel.onMosquitoNetCheckChanged(isChecked) }


        viewModel.isMainProgressShownLiveData.observe(viewLifecycleOwner) { isProgress ->
            windowsillCheckBox.isVisible = !isProgress
            ebbCheckBox.isVisible = !isProgress
            slopeCheckBox.isVisible = !isProgress
            laminationCheckBox.isVisible = !isProgress
            mosquitoNetCheckBox.isVisible = !isProgress
            textView.isVisible = !isProgress
        }
    }

    private fun setupGlassUnitPicker(chipGroup: ChipGroup, textView: TextView) {
        viewModel.isMainProgressShownLiveData.observe(viewLifecycleOwner) { isProgress ->
            chipGroup.isVisible = !isProgress
            textView.isVisible = !isProgress
        }
        chipGroup.setOnCheckedChangeListener { _, checkedId ->
            viewModel.onGlassUnitTypeChanged(ChipIdToGlassUnitTypeConverter.convert(checkedId))
        }
        viewModel.windowModelLiveData.observe(viewLifecycleOwner) { model ->
            chipGroup.check(ChipIdToGlassUnitTypeConverter.convertBack(model.glassUnitType))
        }
    }

    private fun setupHouseTypePicker(chipGroup: ChipGroup, textView: TextView) {
        viewModel.isMainProgressShownLiveData.observe(viewLifecycleOwner) { isProgress ->
            chipGroup.isVisible = !isProgress
            textView.isVisible = !isProgress
            chipGroup.setOnCheckedChangeListener { _, checkedId ->
                viewModel.onHouseTypeChanged(ChipIdToHouseTypeConverter.convert(checkedId))
            }
            viewModel.windowModelLiveData.observe(viewLifecycleOwner) { model ->
                chipGroup.check(ChipIdToHouseTypeConverter.convertBack(model.houseType))
            }
        }
    }

    private fun setupMaterialPicker(textView: TextView, layout: ConstraintLayout) {
        layout.setOnClickListener {
            findNavController().navigate(R.id.action_calculatorFragment_to_selectMaterialTypeBottomSheet)
        }
        viewModel.windowModelLiveData.observe(viewLifecycleOwner) { model ->
            textView.setText(MaterialTypeToStringResConverter.convertToTitleString(model.materialType))
        }
        viewModel.isMainProgressShownLiveData.observe(viewLifecycleOwner) { layout.isVisible = !it }
    }

    private fun setupSashTypesPicker(recyclerView: RecyclerView, textView: TextView) {
        val adapter = SashTypeAdapter { position, newType -> viewModel.onSashTypeChanged(position, newType) }
        recyclerView.adapter = adapter
        viewModel.isMainProgressShownLiveData.observe(viewLifecycleOwner) { isVisible ->
            textView.isVisible = !isVisible
            recyclerView.isVisible = !isVisible
        }
        viewModel.windowModelLiveData.observe(viewLifecycleOwner) { adapter.sashTypes = it.sashes }
    }

    private fun setupWindowTypePicker(chipGroup: ChipGroup, scrollView: HorizontalScrollView, textView: TextView) {
        setupWindowTypeChipGroup(chipGroup)
        setupWindowTypeHorizontalScrollView(scrollView)
        setupWindowTypeTextView(textView)
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
        viewModel.windowModelLiveData.observe(viewLifecycleOwner) { model ->
            chipGroup.check(
                when (model.windowType) {
                    WindowType.ONE_SASH -> R.id.one_sash_chip
                    WindowType.TWO_SASHES -> R.id.two_sashes_chip
                    WindowType.THREE_SASHES -> R.id.three_sashes_chip
                }
            )
        }
    }

    private fun setupWindowTypeHorizontalScrollView(scrollView: HorizontalScrollView) {
        viewModel.isMainProgressShownLiveData.observe(viewLifecycleOwner) { scrollView.isVisible = !it }
    }

    private fun setupWindowTypeTextView(textView: TextView) {
        viewModel.isMainProgressShownLiveData.observe(viewLifecycleOwner) { textView.isVisible = !it }
    }

    private fun mapChipIdToWindowType(@IdRes id: Int) = when (id) {
        R.id.one_sash_chip -> WindowType.ONE_SASH
        R.id.two_sashes_chip -> WindowType.TWO_SASHES
        R.id.three_sashes_chip -> WindowType.THREE_SASHES
        else -> throw IllegalArgumentException("Incorrect chip id: $id")
    }

    private fun setupWindowImageView(imageView: ImageView) {
        viewModel.isMainProgressShownLiveData.observe(viewLifecycleOwner) { imageView.isVisible = !it }
        viewModel.windowModelLiveData.observe(viewLifecycleOwner) { model ->
            imageView.setImageResource(
                when (model.windowType) {
                    WindowType.ONE_SASH -> R.drawable.window_1_sash
                    WindowType.TWO_SASHES -> R.drawable.window_2_sashes
                    WindowType.THREE_SASHES -> R.drawable.window_3_sashes
                }
            )
        }
    }

    private fun setupPriceLayout(textView: TextView, layout: ConstraintLayout, progressBar: ProgressBar) {
        viewModel.isMainProgressShownLiveData.observe(viewLifecycleOwner) { layout.isVisible = !it }
        viewModel.priceLiveData.observe(viewLifecycleOwner) { state ->
            textView.visibility = if (state is State.Success) View.VISIBLE else View.INVISIBLE
            progressBar.isVisible = state is State.Loading
            if (state is State.Success) {
                textView.text = getString(R.string.ruble_placeholder, state.data)
            }
        }
    }

    private fun setupAddToCartButton(button: Button) {
        viewModel.priceLiveData.observe(viewLifecycleOwner) { button.isEnabled = it is State.Success }
    }
}