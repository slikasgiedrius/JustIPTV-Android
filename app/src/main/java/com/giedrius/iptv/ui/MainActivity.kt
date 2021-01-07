package com.giedrius.iptv.ui

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.giedrius.iptv.R
import com.giedrius.iptv.ui.input.InputActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(R.layout.main_activity) {

    private val viewModel: MainActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val navView: BottomNavigationView = findViewById(R.id.bottom_navigation_bar)
        val navController = findNavController(R.id.nav_host_fragment)
        navView.setupWithNavController(navController)

        retrieveInitialUrl()
    }

    private fun retrieveInitialUrl() {
        val initialUrl = viewModel.preferences.getInitialUrl()
        if (initialUrl == null) navigateToInputActivity()
    }

    private fun navigateToInputActivity() {
        val intent = Intent(this, InputActivity::class.java)
        startActivity(intent)
        finish()
    }
}
