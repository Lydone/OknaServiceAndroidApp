package com.lydone.okna_service_android_app.presentation.order.fragment

import android.content.ComponentName
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.browser.customtabs.CustomTabsCallback
import androidx.browser.customtabs.CustomTabsClient
import androidx.browser.customtabs.CustomTabsIntent
import androidx.browser.customtabs.CustomTabsServiceConnection
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.progressindicator.BaseProgressIndicator
import com.google.android.material.progressindicator.LinearProgressIndicator
import com.lydone.okna_service_android_app.MainActivity
import com.lydone.okna_service_android_app.R
import com.lydone.okna_service_android_app.databinding.FragmentOrderBinding
import com.lydone.okna_service_android_app.domain.model.Order
import com.lydone.okna_service_android_app.presentation.common.windowrecyclerview.WindowAdapter
import com.lydone.okna_service_android_app.presentation.converter.StatusToStringResConverter
import com.lydone.okna_service_android_app.presentation.core.PaddingItemDecoration
import com.lydone.okna_service_android_app.presentation.core.State
import com.lydone.okna_service_android_app.presentation.order.model.OrderViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OrderFragment : Fragment(R.layout.fragment_order) {

    private val viewModel by viewModels<OrderViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(OrderFragmentArgs.fromBundle(requireArguments())) {
            viewModel.id = id
        }
        setupTitle()
        setupSubtitle()
        with(FragmentOrderBinding.bind(view)) {
            setupLinearProgressIndicator(linearProgressIndicator)
            setupAddressTitleTextView(addressTitleTextView)
            setupAddressTextView(addressTextView)
            setupPriceTitleTextView(priceTitleTextView)
            setupPriceTextView(priceTextView)
            setupRecyclerView(recyclerView)
            setupPayButton(payButton)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        (activity as? MainActivity)?.supportActionBar?.subtitle = null
    }

    private fun setupTitle() {
        (activity as? MainActivity)?.supportActionBar?.title =
            getString(R.string.order_number_placeholder, viewModel.id)
    }

    private fun setupSubtitle() {
        viewModel.orderStateLiveData.observe(viewLifecycleOwner) { state ->
            (state as? State.Success)?.data?.status?.let { status ->
                (activity as? MainActivity)?.supportActionBar?.setSubtitle(StatusToStringResConverter.convert(status))
            }
        }
    }

    private fun setupAddressTitleTextView(textView: TextView) {
        viewModel.orderStateLiveData.observe(viewLifecycleOwner) { state ->
            textView.isVisible = state is State.Success
            (state as? State.Success)?.data?.let { data ->
                textView.setText(if (data.latitude == 0.0) R.string.pickup_address else R.string.delivery_address)
            }
        }
    }


    private fun setupAddressTextView(textView: TextView) {
        viewModel.orderStateLiveData.observe(viewLifecycleOwner) { state ->
            textView.isVisible = state is State.Success
            (state as? State.Success)?.data?.address?.let { textView.text = it }
        }
    }

    private fun setupPriceTitleTextView(textView: TextView) {
        viewModel.orderStateLiveData.observe(viewLifecycleOwner) { textView.isVisible = it is State.Success }
    }

    private fun setupPriceTextView(textView: TextView) {
        viewModel.orderStateLiveData.observe(viewLifecycleOwner) { state ->
            textView.isVisible = state is State.Success
            (state as? State.Success)?.data?.price?.let { textView.text = getString(R.string.ruble_placeholder, it) }
        }
    }

    private fun setupRecyclerView(recyclerView: RecyclerView) {
        val adapter = WindowAdapter()
        recyclerView.adapter = adapter
        recyclerView.addItemDecoration(
            PaddingItemDecoration(
                paddingStartEnd = resources.getDimensionPixelSize(R.dimen.padding_small),
                paddingTopBottom = resources.getDimensionPixelSize(R.dimen.padding_small),
                paddingMiddle = resources.getDimensionPixelSize(R.dimen.padding_small)
            )
        )
        viewModel.orderStateLiveData.observe(viewLifecycleOwner) { state ->
            recyclerView.isVisible = state is State.Success
            (state as? State.Success)?.data?.windows?.let { adapter.windows = it }
        }
    }

    private fun setupPayButton(button: Button) {
        viewModel.orderStateLiveData.observe(viewLifecycleOwner) { state ->
            button.isVisible = state is State.Success
                    && (state.data.status == Order.Status.CREATED || state.data.status == Order.Status.IN_WORK)
            (state as? State.Success)?.data?.status?.let { status ->
                button.setText(if (status == Order.Status.CREATED) R.string.prepay else R.string.pay)
            }
        }
        button.setOnClickListener { viewModel.onPayButtonClicked() }
        viewModel.urlLiveData.observe(viewLifecycleOwner) {
            CustomTabsClient.bindCustomTabsService(
                this.requireContext(),
                "com.android.chrome",
                object : CustomTabsServiceConnection() {
                    override fun onServiceDisconnected(name: ComponentName?) {
                    }

                    override fun onCustomTabsServiceConnected(name: ComponentName, client: CustomTabsClient) {
                        CustomTabsIntent.Builder(
                            client.newSession(object : CustomTabsCallback() {
                                override fun onNavigationEvent(navigationEvent: Int, extras: Bundle?) {
                                    if (navigationEvent == TAB_HIDDEN) {
                                        viewModel.refreshOrder()
                                    }
                                }
                            })
                        ).build().launchUrl(requireContext(), Uri.parse(it))

                    }

                })
        }
    }

    private fun setupLinearProgressIndicator(indicator: LinearProgressIndicator) {
        //TODO Убрать этот параметр в разметку как только гугл пофиксит
        indicator.showAnimationBehavior = BaseProgressIndicator.SHOW_INWARD
        viewModel.orderStateLiveData.observe(viewLifecycleOwner) { state ->
            if (state is State.Loading) indicator.show() else indicator.hide()
        }
    }
}