package com.lydone.okna_service_android_app.presentation.calculator

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.navGraphViewModels
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.lydone.okna_service_android_app.R
import com.lydone.okna_service_android_app.presentation.calculator.material_type_recycler_view.MaterialTypeAdapter
import com.lydone.okna_service_android_app.presentation.calculator.model.CalculatorViewModel

class SelectMaterialTypeBottomSheet : BottomSheetDialogFragment() {

    private val viewModel by navGraphViewModels<CalculatorViewModel>(R.id.graph_main) { defaultViewModelProviderFactory }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
        inflater.inflate(R.layout.fragment_select_material_type, container, false)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requireDialog().setOnShowListener {
            BottomSheetBehavior.from(requireDialog().findViewById(com.google.android.material.R.id.design_bottom_sheet))
                .apply {
                    skipCollapsed = true
                    state = BottomSheetBehavior.STATE_EXPANDED
                }
        }

        view.findViewById<RecyclerView>(R.id.materials).apply {
            adapter = MaterialTypeAdapter { materialType ->
                viewModel.materialType = materialType
                requireDialog().dismiss()
            }
        }
    }
}