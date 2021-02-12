package com.lydone.okna_service_android_app.presentation.cart.fragment

import android.Manifest
import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.navigation.fragment.findNavController
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.model.LatLng
import com.lydone.okna_service_android_app.R
import com.lydone.okna_service_android_app.databinding.FragmentAddressBinding
import com.lydone.okna_service_android_app.presentation.cart.model.CartViewModel

class AddressFragment : Fragment(R.layout.fragment_address) {

    private var binding: FragmentAddressBinding? = null

    private var mapView: MapView? = null

    private val viewModel by hiltNavGraphViewModels<CartViewModel>(R.id.cart_graph)

    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                mapView?.let { setLocation(it) }
            }
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(FragmentAddressBinding.bind(view).also { binding = it }) {
            this@AddressFragment.mapView = mapView
            mapView.onCreate(savedInstanceState)
            setLocationWithPermissionCheck()
            mapView.getMapAsync { map ->
                map.setOnCameraIdleListener { viewModel.deliveryAddressLatLng = map.cameraPosition.target }
            }
            viewModel.deliveryAddressStringLiveData.observe(viewLifecycleOwner) { addressTextView.text = it }
            setupPickButton(pickButton)
            setupNavigationToLoginGraph()
        }
    }

    private fun setLocationWithPermissionCheck() {
        requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
    }

    @SuppressLint("MissingPermission")
    private fun setLocation(mapView: MapView) {
        mapView.getMapAsync { map ->
            map.isMyLocationEnabled = true
            context?.let { notNullContext ->
                LocationServices.getFusedLocationProviderClient(notNullContext)
                    .lastLocation.addOnSuccessListener { location ->
                        map.moveCamera(
                            CameraUpdateFactory.newLatLngZoom(LatLng(location.latitude, location.longitude), 17f)
                        )
                    }
            }
        }
    }

    private fun setupPickButton(button: Button) {
        button.setOnClickListener { viewModel.createOrder() }
    }

    private fun setupNavigationToLoginGraph() {
        viewModel.navigateToLoginGraphLiveData.observe(viewLifecycleOwner) {
            //TODO add action here
            findNavController().navigate(R.id.graph_login)
        }
    }

    override fun onResume() {
        super.onResume()
        mapView?.onResume()
    }

    override fun onStart() {
        super.onStart()
        mapView?.onStart()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mapView?.onSaveInstanceState(outState)
    }

    override fun onPause() {
        super.onPause()
        mapView?.onPause()
    }

    override fun onStop() {
        super.onStop()
        mapView?.onStop()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView?.onLowMemory()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        mapView?.onDestroy()
        binding = null
    }
}