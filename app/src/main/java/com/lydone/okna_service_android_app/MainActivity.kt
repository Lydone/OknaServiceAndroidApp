package com.lydone.okna_service_android_app

import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(R.layout.activity_main) {

    override fun onStart() {
        super.onStart()
        setupActionBarWithNavController(
            findNavController(R.id.main_nav_host_fragment),
            AppBarConfiguration(setOf(R.id.windowDimensionsFragment, R.id.cartFragment))
        )
        findViewById<BottomNavigationView>(R.id.bottom_navigation).setupWithNavController(findNavController(R.id.main_nav_host_fragment))
    }
}