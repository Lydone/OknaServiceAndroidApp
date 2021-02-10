package com.lydone.okna_service_android_app.presentation.address.fragment

import android.location.Geocoder
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import com.lydone.okna_service_android_app.R
import com.lydone.okna_service_android_app.databinding.FragmentAddressBinding

class AddressFragment : Fragment(R.layout.fragment_address) {

    private var binding: FragmentAddressBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(FragmentAddressBinding.bind(view).also { binding = it }) {
            map.onCreate(savedInstanceState)
            map.getMapAsync { googleMap ->
                googleMap.setOnCameraIdleListener {
                    val addresses = with(googleMap.cameraPosition.target) {
                        Geocoder(context).getFromLocation(latitude, longitude, 1)
                    }
                    Log.d("TAG", googleMap.cameraPosition.target.toString())
                    addressTextView.text = addresses.firstOrNull()?.let { address ->
                        buildString {
                            for (i in 0..address.maxAddressLineIndex) {
                                append("${address.getAddressLine(i)},")
                                deleteCharAt(length - 1)
                            }
                        }
                    }
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        binding?.map?.onResume()
    }

    override fun onStart() {
        super.onStart()
        binding?.map?.onStart()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        binding?.map?.onSaveInstanceState(outState)
    }

    override fun onPause() {
        super.onPause()
        binding?.map?.onPause()
    }

    override fun onStop() {
        super.onStop()
        binding?.map?.onStop()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        binding?.map?.onLowMemory()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        binding?.map?.onDestroy()
        binding = null
    }
}