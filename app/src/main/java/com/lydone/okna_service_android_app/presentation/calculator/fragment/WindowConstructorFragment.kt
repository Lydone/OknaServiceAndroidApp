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
import com.lydone.okna_service_android_app.R
import com.lydone.okna_service_android_app.databinding.FragmentWindowConstructorBinding
import com.lydone.okna_service_android_app.domain.model.WindowType
import com.lydone.okna_service_android_app.presentation.calculator.converter.ChipIdToGlassUnitTypeConverter
import com.lydone.okna_service_android_app.presentation.calculator.converter.ChipIdToHouseTypeConverter
import com.lydone.okna_service_android_app.presentation.calculator.converter.MaterialTypeToStringResConverter
import com.lydone.okna_service_android_app.presentation.calculator.model.WindowConstructorViewModel
import com.lydone.okna_service_android_app.presentation.calculator.recycler.sash.SashTypeAdapter
import com.lydone.okna_service_android_app.presentation.core.State
import dagger.hilt.android.AndroidEntryPoint

@Suppress("WHEN_ENUM_CAN_BE_NULL_IN_JAVA")
@AndroidEntryPoint
class WindowConstructorFragment : Fragment(R.layout.fragment_window_constructor) {

    private val viewModel: WindowConstructorViewModel by hiltNavGraphViewModels(R.id.graph_window_constructor)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(WindowConstructorFragmentArgs.fromBundle(requireArguments())) {
            viewModel.onFragmentAttached(width, height)
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

    private fun setupMainProgressBar(progressBar: ProgressBar) {
        viewModel.isMainProgressShownLiveData.observe(viewLifecycleOwner) { progressBar.isVisible = it }
    }

    private fun setupWindowsillCheckBox(checkBox: CheckBox) {
        checkBox.setOnCheckedChangeListener { _, isChecked -> viewModel.isWindowsillChecked = isChecked }
        viewModel.isWindowsillCheckedLiveData.observe(viewLifecycleOwner) { checkBox.isChecked = it }
        viewModel.isMainProgressShownLiveData.observe(viewLifecycleOwner) { checkBox.isVisible = !it }
    }

    private fun setupEbbCheckBox(checkBox: CheckBox) {
        checkBox.setOnCheckedChangeListener { _, isChecked -> viewModel.isEbbChecked = isChecked }
        viewModel.isEbbCheckedLiveData.observe(viewLifecycleOwner) { checkBox.isChecked = it }
        viewModel.isMainProgressShownLiveData.observe(viewLifecycleOwner) { checkBox.isVisible = !it }
    }

    private fun setupSlopeCheckBox(checkBox: CheckBox) {
        checkBox.setOnCheckedChangeListener { _, isChecked -> viewModel.isSlopeChecked = isChecked }
        viewModel.isSlopeCheckedLiveData.observe(viewLifecycleOwner) { checkBox.isChecked = it }
        viewModel.isMainProgressShownLiveData.observe(viewLifecycleOwner) { checkBox.isVisible = !it }
    }

    private fun setupLaminationCheckBox(checkBox: CheckBox) {
        checkBox.setOnCheckedChangeListener { _, isChecked -> viewModel.isLaminationChecked = isChecked }
        viewModel.isLaminationCheckedLiveData.observe(viewLifecycleOwner) { checkBox.isChecked = it }
        viewModel.isMainProgressShownLiveData.observe(viewLifecycleOwner) { checkBox.isVisible = !it }
    }

    private fun setupMosquitoNetCheckBox(checkBox: CheckBox) {
        checkBox.setOnCheckedChangeListener { _, isChecked -> viewModel.isMosquitoNetChecked = isChecked }
        viewModel.isMosquitoNetCheckedLiveData.observe(viewLifecycleOwner) { checkBox.isChecked = it }
        viewModel.isMainProgressShownLiveData.observe(viewLifecycleOwner) { checkBox.isVisible = !it }
    }

    private fun setupOptionsTextView(textView: TextView) {
        viewModel.isMainProgressShownLiveData.observe(viewLifecycleOwner) { textView.isVisible = !it }
    }

    private fun setupGlassUnitPicker(chipGroup: ChipGroup, textView: TextView) {
        viewModel.isMainProgressShownLiveData.observe(viewLifecycleOwner) { isProgress ->
            chipGroup.isVisible = !isProgress
            textView.isVisible = !isProgress
        }
        chipGroup.setOnCheckedChangeListener { _, checkedId ->
            viewModel.glassUnitType = ChipIdToGlassUnitTypeConverter.convert(checkedId)
        }
        viewModel.glassUnitTypeLiveData.observe(viewLifecycleOwner) { type ->
            chipGroup.check(ChipIdToGlassUnitTypeConverter.convertBack(type))
        }
    }

    private fun setupHouseTypePicker(chipGroup: ChipGroup, textView: TextView) {
        viewModel.isMainProgressShownLiveData.observe(viewLifecycleOwner) { isProgress ->
            chipGroup.isVisible = !isProgress
            textView.isVisible = !isProgress
            chipGroup.setOnCheckedChangeListener { _, checkedId ->
                viewModel.houseType = ChipIdToHouseTypeConverter.convert(checkedId)
            }
            viewModel.houseTypeLiveData.observe(viewLifecycleOwner) { type ->
                chipGroup.check(ChipIdToHouseTypeConverter.convertBack(type))
            }
        }
    }

    private fun setupMaterialPicker(textView: TextView, layout: ConstraintLayout) {
        layout.setOnClickListener {
            findNavController().navigate(R.id.action_calculatorFragment_to_selectMaterialTypeBottomSheet)
        }
        viewModel.materialTypeLiveData.observe(viewLifecycleOwner) { type ->
            textView.setText(MaterialTypeToStringResConverter.convertToTitleString(type))
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
        viewModel.sashesLiveData.observe(viewLifecycleOwner) { adapter.sashTypes = it }
    }

    private fun setupWindowTypePicker(chipGroup: ChipGroup, scrollView: HorizontalScrollView, textView: TextView) {
        chipGroup.setOnCheckedChangeListener { _, checkedId ->
            viewModel.windowType = mapChipIdToWindowType(checkedId)
        }
        viewModel.matchingWindowTypesLiveData.observe(viewLifecycleOwner) { types ->
            for (chip in chipGroup.children) {
                chip.isVisible = mapChipIdToWindowType(chip.id) in types
            }
        }
        viewModel.windowTypeLiveData.observe(viewLifecycleOwner) { type ->
            chipGroup.check(
                when (type) {
                    WindowType.ONE_SASH -> R.id.one_sash_chip
                    WindowType.TWO_SASHES -> R.id.two_sashes_chip
                    WindowType.THREE_SASHES -> R.id.three_sashes_chip
                }
            )
        }
        viewModel.isMainProgressShownLiveData.observe(viewLifecycleOwner) { isProgress ->
            scrollView.isVisible = !isProgress
            textView.isVisible = !isProgress
        }
    }

    private fun mapChipIdToWindowType(@IdRes id: Int) = when (id) {
        R.id.one_sash_chip -> WindowType.ONE_SASH
        R.id.two_sashes_chip -> WindowType.TWO_SASHES
        R.id.three_sashes_chip -> WindowType.THREE_SASHES
        else -> throw IllegalArgumentException("Incorrect chip id: $id")
    }

    private fun setupWindowImageView(imageView: ImageView) {
        viewModel.isMainProgressShownLiveData.observe(viewLifecycleOwner) { imageView.isVisible = !it }
        viewModel.windowTypeLiveData.observe(viewLifecycleOwner) { type ->
            imageView.setImageResource(
                when (type) {
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
        button.setOnClickListener {
            viewModel.onAddToCartButtonClicked()
        }
        viewModel.navigateToCartLiveData.observe(viewLifecycleOwner) {
            findNavController().navigate(R.id.action_calculatorFragment_to_cartFragment)
        }
    }
}