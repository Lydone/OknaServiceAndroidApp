package com.lydone.okna_service_android_app.presentation.calculator.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Button
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.core.view.postDelayed
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.ar.core.Config
import com.google.ar.sceneform.AnchorNode
import com.google.ar.sceneform.rendering.*
import com.lydone.okna_service_android_app.R
import com.lydone.okna_service_android_app.databinding.FragmentArMeasurementBinding
import com.lydone.okna_service_android_app.presentation.calculator.model.ArMeasurementViewModel
import com.lydone.okna_service_android_app.presentation.calculator.model.WindowDimensions
import com.lydone.okna_service_android_app.presentation.core.RequestKeys
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
@SuppressLint("NewApi")
class ArMeasurementFragment : Fragment(R.layout.fragment_ar_measurement) {

    private val viewModel by viewModels<ArMeasurementViewModel>()

    private var currentAnchorNode: AnchorNode? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onResume() {
        super.onResume()
        with(FragmentArMeasurementBinding.bind(requireView())) {
            (childFragmentManager.findFragmentById(R.id.fragment_container_view) as ArSceneFragment).let { arFragment ->
                setupArFragment(arFragment)
                setupAddCornerButton(addCornerButton, arFragment)
                setupRemovePreviousPointButton(removePreviousPointButton, arFragment)
                setupConfirmButton(confirmButton)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.ar_measurement, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            R.id.tutorial_item -> {
                findNavController().navigate(ArMeasurementFragmentDirections.tutorialAction())
                true
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }

    private fun setupArFragment(arFragment: ArSceneFragment) {
        val arSceneView = arFragment.arSceneView
        val scene = arSceneView.scene
        arFragment.lifecycleScope.launchWhenResumed {
            arSceneView.session?.configure(
                arSceneView.session?.config?.apply {
                    focusMode = Config.FocusMode.AUTO
                    planeFindingMode = Config.PlaneFindingMode.DISABLED
                }
            )
        }
        arSceneView.postDelayed(3000) {
            arFragment.planeDiscoveryController.hide()
        }
        scene.addOnUpdateListener {
            val frame = arSceneView.arFrame ?: return@addOnUpdateListener
            viewModel.createNode(
                frame.hitTest(
                    arSceneView.x + arSceneView.width / 2,
                    arSceneView.y + arSceneView.height / 2
                )
            )?.let { newNode ->
                currentAnchorNode?.let { scene.removeChild(it) }
                scene.addChild(newNode)
                currentAnchorNode = newNode
            }
        }
    }

    private fun setupAddCornerButton(button: Button, arFragment: ArSceneFragment) {
        val arSceneView = arFragment.arSceneView
        button.setOnClickListener {
            currentAnchorNode?.let { arSceneView.scene.addChild(viewModel.onAddCornerNodeButtonClicked(it)) }
        }
        viewModel.isAddCornerButtonVisibleLiveData.observe(viewLifecycleOwner) { button.isVisible = it }
        viewModel.addCornerButtonTextResLiveData.observe(viewLifecycleOwner) { button.setText(it) }
    }

    private fun setupRemovePreviousPointButton(button: Button, arFragment: ArSceneFragment) {
        button.setOnClickListener {
            viewModel.onRemovePreviousPointButtonClicked()?.let { arFragment.arSceneView.scene.removeChild(it) }
        }
    }

    private fun setupConfirmButton(button: Button) {
        viewModel.isAddCornerButtonVisibleLiveData.observe(viewLifecycleOwner) { button.isVisible = !it }
        button.setOnClickListener {
            setFragmentResult(
                RequestKeys.KEY_WINDOW_DIMENSIONS,
                bundleOf(
                    RequestKeys.KEY_WINDOW_DIMENSIONS to WindowDimensions(
                        width = viewModel.calculateWindowWidth(),
                        height = viewModel.calculateWindowHeight()
                    )
                )
            )
            findNavController().popBackStack()
        }
    }
}