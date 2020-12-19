package com.giedrius.iptv.ui.settings

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.giedrius.iptv.R
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.settings_fragment.*

@AndroidEntryPoint
class SettingsFragment : Fragment(R.layout.settings_fragment) {

    private val viewModel: SettingsViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupListeners()
    }

    private fun setupListeners() {
        iwClearSharedPreferences.setOnClickListener { viewModel.clearSharedPreferences() }
        iwDeleteAllChannels.setOnClickListener { viewModel.deleteAllChannels() }
        iwDeleteAllFavouriteChannels.setOnClickListener { viewModel.deleteAllFavouriteChannels() }
        bIntroductionScreen.setOnClickListener { startIntroductionActivity() }
    }

    private fun startIntroductionActivity() {
        val action = SettingsFragmentDirections.actionSettingsFragmentToIntroductionActivity()
        view?.findNavController()?.navigate(action)
    }
}