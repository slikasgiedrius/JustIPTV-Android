package com.giedrius.iptv.ui.introduction

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.giedrius.iptv.R
import com.giedrius.iptv.databinding.ActivityIntroductionBinding
import com.giedrius.iptv.ui.MainActivity
import com.shuhart.stepview.StepView
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
        styleStepView()
    }

    private fun handleObservers() {
        viewModel.downloadRepository.onDataDownloaded.observe(this) {
            binding.stepView.go(1, true)
        }
        viewModel.downloadRepository.onFilePatchChanged.observe(this) {
            viewModel.loadChannels(it)
        }
        viewModel.onExtractionCompleted.observe(this) {
            binding.stepView.go(2, true)
        }
        viewModel.onSavingCompleted.observe(this) {
            binding.stepView.go(3, true)
        }
        viewModel.channelsRepository.savedChannels.observe(this) {
            if (it.count() != 0) {
                binding.stepView.go(4, true)
                navigateToMainActivity()
            }
        }
    }

    private fun navigateToMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun styleStepView() {
        binding.stepView.state
            .steps(listOf(
                "Download content from URL",
                "Extract items from content",
                "Save items to local storage",
                "Retrieve items from local storage")
            )
            .animationType(StepView.ANIMATION_ALL)
            .animationDuration(resources.getInteger(android.R.integer.config_mediumAnimTime))
            .commit()
    }
}