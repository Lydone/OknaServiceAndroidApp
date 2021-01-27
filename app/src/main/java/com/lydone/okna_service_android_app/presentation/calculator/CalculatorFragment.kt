package com.lydone.okna_service_android_app.presentation.calculator

import android.os.Bundle
import android.view.View
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButtonToggleGroup
import com.google.android.material.chip.ChipGroup
import com.google.android.material.slider.Slider
import com.lydone.okna_service_android_app.R
import com.lydone.okna_service_android_app.presentation.calculator.converter.*
import com.lydone.okna_service_android_app.presentation.calculator.model.CalculatorViewModel
import com.lydone.okna_service_android_app.presentation.calculator.sash_type_recycler_view.SashTypeAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CalculatorFragment : Fragment(R.layout.fragment_calculator) {

    private val viewModel: CalculatorViewModel by navGraphViewModels(R.id.graph_main) { defaultViewModelProviderFactory }

    //    private lateinit var windowImageViewPager: ViewPager2
    private lateinit var windowImageView: ImageView
    private lateinit var sashesCountChipGroup: MaterialButtonToggleGroup
    private lateinit var windowWidthTextView: TextView
    private lateinit var windowWidthSlider: Slider
    private lateinit var windowHeightTextView: TextView
    private lateinit var windowHeightSlider: Slider
    private lateinit var sashTypesRecyclerView: RecyclerView
    private lateinit var materialTextView: TextView
    private lateinit var glassUnitChipGroup: ChipGroup
    private lateinit var houseTypeChipGroup: ChipGroup

    private lateinit var windowsillCheckBox: CheckBox
    private lateinit var ebbCheckBox: CheckBox
    private lateinit var slopeCheckBox: CheckBox
    private lateinit var laminationCheckBox: CheckBox
    private lateinit var mosquitoNetCheckBox: CheckBox

    private lateinit var installationCheckBox: CheckBox
    private lateinit var deliveryCheckBox: CheckBox

    private lateinit var sashTypeAdapter: SashTypeAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        windowImageViewPager = view.findViewById<ViewPager2>(R.id.window_image).apply {
//            adapter = WindowImagesAdapter()
//            setPageTransformer { page, position ->
//                page.alpha = when {
//                    position < -1 || position > 1 -> 0f
//                    else -> 1 - (abs(position) * 5).coerceAtMost(1f)
//                }
//            }
//        }
        setupWindowImageView(view)
        setupSashesCountToggleGroup(view)
//        setupWindowWidthTextView(view)
//        setupWindowWidthSlider(view)
//        setupWindowHeightTextView(view)
//        setupWindowHeightSlider(view)
        setupSashTypesRecycler(view)
        setupMaterialTextView(view)
        setupGlassUnitChipGroup(view)
        setupHouseTypeChipGroup(view)
        setupOptionsCheckBoxes(view)

        installationCheckBox = view.findViewById<CheckBox>(R.id.installation).apply {
            setOnCheckedChangeListener { _, isChecked -> viewModel.isInstallationSelected = isChecked }
            isChecked = viewModel.isInstallationSelected
        }
        deliveryCheckBox = view.findViewById<CheckBox>(R.id.delivery).apply {
            setOnCheckedChangeListener { _, isChecked -> viewModel.isDeliverySelected = isChecked }
            isChecked = viewModel.isDeliverySelected
        }
    }

    private fun setupOptionsCheckBoxes(view: View) {
        windowsillCheckBox = view.findViewById<CheckBox>(R.id.windowsill).apply {
            setOnCheckedChangeListener { _, isChecked -> viewModel.isWindowsillSelected = isChecked }
            isChecked = viewModel.isWindowsillSelected
        }
        ebbCheckBox = view.findViewById<CheckBox>(R.id.ebb).apply {
            setOnCheckedChangeListener { _, isChecked -> viewModel.isEbbSelected = isChecked }
            isChecked = viewModel.isEbbSelected
        }
        slopeCheckBox = view.findViewById<CheckBox>(R.id.slope).apply {
            setOnCheckedChangeListener { _, isChecked -> viewModel.isSlopeSelected = isChecked }
            isChecked = viewModel.isSlopeSelected
        }
        laminationCheckBox = view.findViewById<CheckBox>(R.id.lamination).apply {
            setOnCheckedChangeListener { _, isChecked -> viewModel.isLaminationSelected = isChecked }
            isChecked = viewModel.isLaminationSelected
        }
        mosquitoNetCheckBox = view.findViewById<CheckBox>(R.id.mosquito_net).apply {
            setOnCheckedChangeListener { _, isChecked -> viewModel.isMosquitoNetSelected = isChecked }
            isChecked = viewModel.isMosquitoNetSelected
        }
    }

    private fun setupHouseTypeChipGroup(view: View) {
        houseTypeChipGroup = view.findViewById<ChipGroup>(R.id.house_type).apply {
            setOnCheckedChangeListener { _, checkedId ->
                viewModel.houseType = ChipIdToHouseTypeConverter.convert(checkedId)
            }
            check(ChipIdToHouseTypeConverter.convertBack(viewModel.houseType))
        }
    }

    private fun setupGlassUnitChipGroup(view: View) {
        glassUnitChipGroup = view.findViewById<ChipGroup>(R.id.glass_unit).apply {
            setOnCheckedChangeListener { _, checkedId ->
                viewModel.glassUnitType = ChipIdToGlassUnitTypeConverter.convert(checkedId)
            }
            check(ChipIdToGlassUnitTypeConverter.convertBack(viewModel.glassUnitType))
        }
    }

    private fun setupMaterialTextView(view: View) {
        view.findViewById<ConstraintLayout>(R.id.material).setOnClickListener {
            findNavController().navigate(R.id.action_calculatorFragment_to_selectMaterialTypeBottomSheet)
        }
        materialTextView = view.findViewById<TextView>(R.id.material_name).also { textView ->
            viewModel.materialTypeLiveData.observe(viewLifecycleOwner) { type ->
                textView.setText(MaterialTypeToStringResConverter.convertToTitleString(type))
            }
        }
    }

    private fun setupSashTypesRecycler(view: View) {
        sashTypeAdapter = SashTypeAdapter { position, newType -> viewModel.onSashTypeChanged(position, newType) }
        sashTypesRecyclerView = view.findViewById<RecyclerView>(R.id.sash_types).apply {
            adapter = sashTypeAdapter
        }
        viewModel.sashTypesLiveData.observe(viewLifecycleOwner) { sashTypeAdapter.sashTypes = it }
    }

//    private fun setupWindowWidthTextView(view: View) {
//        windowWidthTextView = view.findViewById<TextView>(R.id.select_window_width_title).also { textView ->
//            viewModel.windowWidthLiveData.observe(viewLifecycleOwner) { width ->
//                textView.text = getString(R.string.window_width_placeholder, width)
//            }
//        }
//    }

//    private fun setupWindowWidthSlider(view: View) {
//        windowWidthSlider = view.findViewById<Slider>(R.id.window_width_slider).also { slider ->
//            viewModel.windowSizeLimitsLiveData.observe(viewLifecycleOwner, { limits ->
//                slider.apply {
//                    value = value.coerceIn(limits.minWidth.toFloat(), limits.maxWidth.toFloat())
//                    valueFrom = limits.minWidth.toFloat()
//                    valueTo = limits.maxWidth.toFloat()
//                }
//            })
//            slider.addOnChangeListener { _, value, _ -> viewModel.windowWidth = value.toInt() }
//            slider.value = viewModel.windowWidth.toFloat()
//        }
//    }

//    private fun setupWindowHeightTextView(view: View) {
//        windowHeightTextView = view.findViewById<TextView>(R.id.select_window_height_title).also { textView ->
//            viewModel.windowHeightLiveData.observe(viewLifecycleOwner) { height ->
//                textView.text = getString(R.string.window_height_placeholder, height)
//            }
//        }
//    }

    private fun setupWindowHeightSlider(view: View) {
        windowHeightSlider = view.findViewById<Slider>(R.id.height_slider).also { slider ->
            viewModel.windowSizeLimitsLiveData.observe(viewLifecycleOwner, { limits ->
                slider.apply {
                    value = value.coerceIn(limits.minHeight.toFloat(), limits.maxHeight.toFloat())
                    valueFrom = limits.minHeight.toFloat()
                    valueTo = limits.maxHeight.toFloat()
                }
            })
            slider.addOnChangeListener { _, value, _ -> viewModel.windowHeight = value.toInt() }
            slider.value = viewModel.windowHeight.toFloat()
        }
    }

    private fun setupSashesCountToggleGroup(view: View) {
        sashesCountChipGroup = view.findViewById<MaterialButtonToggleGroup>(R.id.sashes_count_selection).apply {
//            setOnCheckedChangeListener { _, checkedId ->
//                viewModel.windowSashesCount = ChipIdToWindowSashesCountConverter.convert(checkedId)
//            }
            addOnButtonCheckedListener { _, checkedId, isChecked ->
                if (isChecked) {
                    viewModel.updateSashesNumber(ChipIdToWindowSashesCountConverter.convert(checkedId))
                }
            }
            check(ChipIdToWindowSashesCountConverter.convertBack(viewModel.sashTypes.size))
        }
    }

    private fun setupWindowImageView(view: View) {
        windowImageView = view.findViewById<ImageView>(R.id.window_image).also { imageView ->
            viewModel.sashTypesLiveData.observe(viewLifecycleOwner) { sashTypes ->
                imageView.setImageResource(WindowSashesCountToDrawableResConverter.convert(sashTypes.size))
            }
        }
    }
}