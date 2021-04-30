package com.lydone.okna_service_android_app.presentation.profile.fragment

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.progressindicator.BaseProgressIndicator
import com.google.android.material.progressindicator.LinearProgressIndicator
import com.lydone.okna_service_android_app.R
import com.lydone.okna_service_android_app.databinding.FragmentProfileBinding
import com.lydone.okna_service_android_app.presentation.core.PaddingItemDecoration
import com.lydone.okna_service_android_app.presentation.core.RequestKeys
import com.lydone.okna_service_android_app.presentation.core.State
import com.lydone.okna_service_android_app.presentation.profile.model.ProfileViewModel
import com.lydone.okna_service_android_app.presentation.profile.recyclerview.OrderAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : Fragment(R.layout.fragment_profile) {

    private val viewModel by viewModels<ProfileViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        with(FragmentProfileBinding.bind(view)) {
            setupLinearProgressIndicator(linearProgressIndicator)
            setupNameTextView(nameTextView)
            setupEmailTextView(emailTextView)
            setupPhoneNumberTextView(phoneNumberTextView)
            setupOrdersRecyclerView(ordersRecyclerView)
        }
        setupNavigation()
        setLoggedInFragmentResultListener()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.profile, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.logout_item -> {
                viewModel.onLogoutMenuItemClicked()
                true
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }

    private fun setupLinearProgressIndicator(indicator: LinearProgressIndicator) {
        //TODO Убрать этот параметр в разметку как только гугл пофиксит
        indicator.showAnimationBehavior = BaseProgressIndicator.SHOW_INWARD
        viewModel.dataStateLiveData.observe(viewLifecycleOwner) { state ->
            if (state is State.Loading) indicator.show() else indicator.hide()
        }
    }

    private fun setupNameTextView(textView: TextView) {
        viewModel.dataStateLiveData.observe(viewLifecycleOwner) { state ->
            textView.isVisible = state is State.Success
            if (state is State.Success) {
                textView.text = state.data.userInfo.name
            }
        }
    }

    private fun setupEmailTextView(textView: TextView) {
        viewModel.dataStateLiveData.observe(viewLifecycleOwner) { state ->
            textView.isVisible = state is State.Success
            if (state is State.Success) {
                textView.text = state.data.userInfo.email
            }
        }
    }

    private fun setupPhoneNumberTextView(textView: TextView) {
        viewModel.dataStateLiveData.observe(viewLifecycleOwner) { state ->
            textView.isVisible = state is State.Success
            if (state is State.Success) {
                textView.text = getString(R.string.plus_placeholder, state.data.userInfo.phoneNumber)
            }
        }
    }

    private fun setupOrdersRecyclerView(recyclerView: RecyclerView) {
        val adapter = OrderAdapter(onClick = { order ->
            findNavController().navigate(ProfileFragmentDirections.showOrderInfoAction(order.id))
        })
        with(recyclerView) {
            this.adapter = adapter
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
            addItemDecoration(
                PaddingItemDecoration(
                    paddingStartEnd = resources.getDimensionPixelSize(R.dimen.padding_medium),
                    paddingTopBottom = 0,
                    paddingMiddle = resources.getDimensionPixelSize(R.dimen.padding_small)
                )
            )
        }
        viewModel.dataStateLiveData.observe(viewLifecycleOwner) { state ->
            recyclerView.isVisible = state is State.Success
            if (state is State.Success) {
                adapter.orders = state.data.orders
            }
        }
    }

    private fun setupNavigation() {
        viewModel.navDirectionsLiveData.observe(viewLifecycleOwner) { findNavController().navigate(it) }
    }

    private fun setLoggedInFragmentResultListener() {
        setFragmentResultListener(RequestKeys.KEY_LOGGED_IN) { _, _ -> viewModel.loadData() }
    }
}