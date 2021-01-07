package com.giedrius.iptv.ui.introduction

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.giedrius.iptv.ui.MainActivity
import com.giedrius.iptv.R
import com.giedrius.iptv.databinding.ActivityIntroductionBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class IntroductionActivity : AppCompatActivity(R.layout.activity_introduction) {

    private lateinit var binding: ActivityIntroductionBinding

    private val viewModel: IntroductionViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityIntroductionBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        handleObservers()
    }

    private fun handleObservers() {
        viewModel.downloadRepository.onDataDownloaded.observe(this) {
            binding.sDownloadContent.isChecked = it
        }
        viewModel.downloadRepository.onFilePatchChanged.observe(this) {
            viewModel.loadChannels(it)
        }
        viewModel.onExtractionCompleted.observe(this) {
            binding.sExtractItems.isChecked = it
        }
        viewModel.onSavingCompleted.observe(this) {
            binding.sSaveItems.isChecked = true
        }
        viewModel.channelsRepository.savedChannels.observe(this) {
            if (it.count() != 0) {
                binding.sDownloadContent.isChecked = true
                binding.sExtractItems.isChecked = true
                binding.sSaveItems.isChecked = true
                binding.sRetrieveItems.isChecked = true

                navigateToMainActivity()
            }
        }
    }

    private fun navigateToMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}