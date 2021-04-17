package com.lydone.okna_service_android_app.presentation.calculator.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.lydone.okna_service_android_app.R
import com.lydone.okna_service_android_app.databinding.FragmentArTutorialBinding

class ArTutorialBottomSheet : BottomSheetDialogFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_ar_tutorial, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(FragmentArTutorialBinding.bind(view)) {
            gotItButton.setOnClickListener { dismiss() }
        }
    }
}