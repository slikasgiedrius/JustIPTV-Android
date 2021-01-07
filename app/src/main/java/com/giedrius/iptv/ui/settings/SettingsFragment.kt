package com.giedrius.iptv.ui.settings

import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.giedrius.iptv.R
import com.giedrius.iptv.databinding.FavouritesFragmentBinding
import com.giedrius.iptv.databinding.SettingsFragmentBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingsFragment : Fragment(R.layout.settings_fragment) {

    private var _binding: SettingsFragmentBinding? = null
    private val binding get() = _binding!!

    private val viewModel: SettingsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = SettingsFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupListeners()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupListeners() {
        binding.iwClearSharedPreferences.setOnClickListener { viewModel.clearSharedPreferences() }
        binding.iwDeleteAllChannels.setOnClickListener { viewModel.deleteAllChannels() }
        binding.iwDeleteAllFavouriteChannels.setOnClickListener { viewModel.deleteAllFavouriteChannels() }
        binding.bComposeScreen.setOnClickListener { startComposeActivity() }
        binding.bIntroduction.setOnClickListener { startIntroductionActivity() }
        binding.bTheme.setOnClickListener {
            when (resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) {
                Configuration.UI_MODE_NIGHT_YES ->
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                Configuration.UI_MODE_NIGHT_NO ->
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            }
        }
    }

    private fun startComposeActivity() {
        val action = SettingsFragmentDirections.actionSettingsFragmentToComposeActivity()
        view?.findNavController()?.navigate(action)
    }

    private fun startIntroductionActivity() {
        val action = SettingsFragmentDirections.actionSettingsFragmentToIntroductionActivity()
        view?.findNavController()?.navigate(action)
    }
}