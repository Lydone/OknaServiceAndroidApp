package com.lydone.okna_service_android_app

import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.asLiveData
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.lydone.okna_service_android_app.domain.interactor.CartInteractor
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity(R.layout.activity_main) {

    @Inject
    lateinit var cartInteractor: CartInteractor

    override fun onStart() {
        super.onStart()
        setupActionBarWithNavController(
            findNavController(R.id.main_nav_host_fragment),
            AppBarConfiguration(setOf(R.id.windowDimensionsFragment, R.id.cartFragment))
        )
        findViewById<BottomNavigationView>(R.id.bottom_navigation).let { bottomNavigationView ->
            bottomNavigationView.setupWithNavController(findNavController(R.id.main_nav_host_fragment))
            cartInteractor.getWindows().asLiveData().observe(this) { list ->
                bottomNavigationView.getOrCreateBadge(R.id.cart_graph).isVisible = list.isNotEmpty()
                bottomNavigationView.getOrCreateBadge(R.id.cart_graph).number = list.size
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (item.itemId == android.R.id.home) {
            findNavController(R.id.main_nav_host_fragment).popBackStack()
        } else super.onOptionsItemSelected(item)
    }
}