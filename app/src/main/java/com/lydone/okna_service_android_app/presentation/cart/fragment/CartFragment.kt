package com.lydone.okna_service_android_app.presentation.cart.fragment

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.CheckBox
import android.widget.FrameLayout
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.chip.ChipGroup
import com.google.android.material.progressindicator.BaseProgressIndicator
import com.google.android.material.progressindicator.LinearProgressIndicator
import com.lydone.okna_service_android_app.R
import com.lydone.okna_service_android_app.databinding.FragmentCartBinding
import com.lydone.okna_service_android_app.presentation.cart.converter.ChipIdToHouseTypeConverter
import com.lydone.okna_service_android_app.presentation.cart.model.CartViewModel
import com.lydone.okna_service_android_app.presentation.cart.recycler.WindowAdapter
import com.lydone.okna_service_android_app.presentation.core.PaddingItemDecoration
import com.lydone.okna_service_android_app.presentation.core.RequestKeys
import com.lydone.okna_service_android_app.presentation.core.State

class CartFragment : Fragment(R.layout.fragment_cart) {

    private val viewModel by hiltNavGraphViewModels<CartViewModel>(R.id.cart_graph)

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
            setupLinearProgressIndicator(linearProgressIndicator)
            setupFullscreenProgressFrameLayout(fullscreenProgressFrameLayout)
        }
        setupNavigation()
        setLoggedInFragmentResultListener()
    }

    private fun setupLinearProgressIndicator(indicator: LinearProgressIndicator) {
        //TODO Убрать этот параметр в разметку как только гугл пофиксит
        indicator.showAnimationBehavior = BaseProgressIndicator.SHOW_INWARD
        viewModel.windowsLiveData.observe(viewLifecycleOwner) { windows ->
            if (windows == null) indicator.show() else indicator.hide()
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
            recyclerView.isVisible = !windows.isNullOrEmpty()
            adapter.windows = windows ?: emptyList()
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
        viewModel.windowsLiveData.observe(viewLifecycleOwner) { layout.isVisible = !it.isNullOrEmpty() }
    }

    private fun setupEmptyCartLayout(layout: ConstraintLayout, goToCalculatorButton: Button) {
        viewModel.windowsLiveData.observe(viewLifecycleOwner) { layout.isVisible = it?.isEmpty() == true }
        goToCalculatorButton.setOnClickListener { findNavController().navigate(R.id.action_cartFragment_to_window_dimensions) }
    }

    private fun setupCreateOrderButton(button: Button) {
        viewModel.priceLiveData.observe(viewLifecycleOwner) { state ->
            button.isEnabled = state is State.Success
            button.text = when (state) {
                is State.Error -> getString(R.string.unable_to_calculate_price)
                is State.Loading -> getString(R.string.calculating_price)
                is State.Success -> getString(R.string.create_order_placeholder, state.data)
            }
            button.setOnClickListener {
                if (viewModel.isDeliveryIncluded) {
                    findNavController().navigate(CartFragmentDirections.actionCartFragmentToAddressFragment())
                } else {
                    viewModel.createOrder()
                }
            }
        }
    }

    private fun setupFullscreenProgressFrameLayout(frameLayout: FrameLayout) {
        viewModel.isFullscreenProgressShownLiveData.observe(viewLifecycleOwner) { frameLayout.isVisible = it }
    }

    private fun setupNavigation() {
        viewModel.navDirectionsLiveData.observe(viewLifecycleOwner) { findNavController().navigate(it) }
    }

    private fun setLoggedInFragmentResultListener() {
        setFragmentResultListener(RequestKeys.KEY_LOGGED_IN) { _, _ -> viewModel.createOrder() }
    }
}