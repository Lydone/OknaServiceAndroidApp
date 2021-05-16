package com.lydone.okna_service_android_app.presentation.contacts.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.lydone.okna_service_android_app.R
import com.lydone.okna_service_android_app.databinding.FragmentContactsBinding
import com.lydone.okna_service_android_app.presentation.contacts.recyclerview.OfficeAdapter
import com.lydone.okna_service_android_app.presentation.core.PaddingItemDecoration
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ContactsFragment : Fragment(R.layout.fragment_contacts) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(FragmentContactsBinding.bind(view)) {
            recyclerView.adapter = OfficeAdapter()
            recyclerView.addItemDecoration(
                PaddingItemDecoration(
                    paddingStartEnd = resources.getDimensionPixelSize(R.dimen.margin_medium),
                    paddingTopBottom = 0,
                    paddingMiddle = resources.getDimensionPixelSize(R.dimen.margin_medium)
                )
            )
        }
    }
}