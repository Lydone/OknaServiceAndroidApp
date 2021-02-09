package com.lydone.okna_service_android_app.presentation.cart.fragment

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.CheckBox
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.chip.ChipGroup
import com.lydone.okna_service_android_app.R
import com.lydone.okna_service_android_app.databinding.FragmentCartBinding
import com.lydone.okna_service_android_app.presentation.cart.converter.ChipIdToHouseTypeConverter
import com.lydone.okna_service_android_app.presentation.cart.model.CartViewModel
import com.lydone.okna_service_android_app.presentation.cart.recycler.WindowAdapter
import com.lydone.okna_service_android_app.presentation.core.PaddingItemDecoration
import com.lydone.okna_service_android_app.presentation.core.State
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CartFragment : Fragment(R.layout.fragment_cart) {

    private val viewModel by viewModels<CartViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(FragmentCartBinding.bind(view)) {
            setupRecyclerView(recyclerView)
            setupBottomLayout(bottomLayout)
            setupEmptyCartLayout(emptyCartLayout, goToCalculatorButton)
            setupCreateOrderButton(createOrderButton)
            setupHouseChipGroup(houseTypeChipGroup)
            setupDeliveryCheckBox(deliveryCheckBox)
            setupInstallationCheckBox(installationCheckBox)
        }
    }

    private fun setupRecyclerView(recyclerView: RecyclerView) {
        val adapter = WindowAdapter(
            onChangeButtonClicked = { window ->
                findNavController().navigate(
                    CartFragmentDirections.actionCartFragmentToGraphWindowConstructor(
                        id = requireNotNull(window.id)
                    )
                )
            },
            onDeleteButtonClicked = { viewModel.onDeleteWindowButtonClicked(it) }
        )
        recyclerView.adapter = adapter
        recyclerView.addItemDecoration(
            PaddingItemDecoration(
                paddingStartEnd = resources.getDimensionPixelSize(R.dimen.padding_small),
                paddingTopBottom = resources.getDimensionPixelSize(R.dimen.padding_small),
                paddingMiddle = resources.getDimensionPixelSize(R.dimen.padding_small)
            )
        )
        viewModel.windowsLiveData.observe(viewLifecycleOwner) { windows ->
            recyclerView.isVisible = windows.isNotEmpty()
            adapter.windows = windows
        }
    }

    private fun setupHouseChipGroup(chipGroup: ChipGroup) {
        viewModel.houseTypeLiveData.observe(viewLifecycleOwner) { type ->
            chipGroup.check(ChipIdToHouseTypeConverter.convertBack(type))
        }
        chipGroup.setOnCheckedChangeListener { _, checkedId ->
            viewModel.houseType = ChipIdToHouseTypeConverter.convert(checkedId)
        }
    }

    private fun setupDeliveryCheckBox(checkBox: CheckBox) {
        viewModel.isDeliveryIncludedLiveData.observe(viewLifecycleOwner) { checkBox.isChecked = it }
        checkBox.setOnCheckedChangeListener { _, isChecked -> viewModel.isDeliveryIncluded = isChecked }
    }

    private fun setupInstallationCheckBox(checkBox: CheckBox) {
        viewModel.isInstallationIncludedLiveData.observe(viewLifecycleOwner) { checkBox.isChecked = it }
        checkBox.setOnCheckedChangeListener { _, isChecked -> viewModel.isInstallationIncluded = isChecked }
    }

    private fun setupBottomLayout(layout: ConstraintLayout) {
        viewModel.windowsLiveData.observe(viewLifecycleOwner) { layout.isVisible = it.isNotEmpty() }
    }

    private fun setupEmptyCartLayout(layout: ConstraintLayout, goToCalculatorButton: Button) {
        viewModel.windowsLiveData.observe(viewLifecycleOwner) { layout.isVisible = it.isEmpty() }
        goToCalculatorButton.setOnClickListener { findNavController().navigate(R.id.action_cartFragment_to_windowDimensionsFragment) }
    }

    private fun setupCreateOrderButton(button: Button) {
        viewModel.priceLiveData.observe(viewLifecycleOwner) { state ->
            button.isEnabled = state is State.Success
            if (state is State.Success) {
                button.text = getString(R.string.create_order_placeholder, state.data)
            } else {
                button.setText(R.string.calculating_price)
            }
        }
    }
}