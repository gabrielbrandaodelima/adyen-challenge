package com.adyen.android.assignment.main.ui.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.findNavController
import com.adyen.android.assignment.R
import com.adyen.android.assignment.core.extensions.viewBinding
import com.adyen.android.assignment.databinding.ActivityMainBinding
import com.adyen.android.assignment.main.ui.view.fragment.MainFragment

class MainActivity : AppCompatActivity() {

    private val listener  =
        NavController.OnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.mainFragment -> binding.toolbar.setNavigationIcon(null)
                R.id.venueDetailsFragment -> binding.toolbar.setNavigationIcon(R.drawable.baseline_arrow_back_black_24dp)
            }
        }
    private val binding by viewBinding(ActivityMainBinding::inflate)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setUpToolbar()
    }

    override fun onStart() {
        super.onStart()
        setUpNavigationChangedListener()
    }

    private fun setUpNavigationChangedListener() {
        findNavController(R.id.main_nav_host_fragment).addOnDestinationChangedListener(listener)
    }

    private fun setUpToolbar() {
        binding.toolbar.apply {
            setNavigationOnClickListener { this@MainActivity.onBackPressed() }
            title = getString(R.string.foursquare_venues)
        }
    }
    companion object{
        val RECYCLER_VIEW_STATE_KEY = "RECYCLER_VIEW_STATE_KEY"
        val REQUEST_LOCATION_PERMISSION: Int = 11
    }
}