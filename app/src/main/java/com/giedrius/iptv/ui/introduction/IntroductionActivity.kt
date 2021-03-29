package com.giedrius.iptv.ui.introduction

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.giedrius.iptv.BuildConfig
import com.giedrius.iptv.R
import com.giedrius.iptv.databinding.ActivityIntroductionBinding
import com.giedrius.iptv.ui.MainActivity
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

        setupInitialUrl()
        handleObservers()
        setupListeners()
        styleStepView()
    }

    private fun setupInitialUrl() = binding.editTextTextMultiLine.setText(BuildConfig.MY_URL)

    private fun handleObservers() {
        viewModel.onUrlIsValid.observe(this) {
            binding.stepView.go(SETUP_STEPS[0].first, true)
            viewModel.downloadContent()
        }
        viewModel.downloadRepository.onDataDownloaded.observe(this) {
            binding.stepView.go(SETUP_STEPS[1].first, true)
        }
        viewModel.downloadRepository.onFilePatchChanged.observe(this) {
            viewModel.loadChannels(it)
        }
        viewModel.onExtractionCompleted.observe(this) {
            binding.stepView.go(SETUP_STEPS[2].first, true)
        }
        viewModel.onSavingCompleted.observe(this) {
            binding.stepView.go(SETUP_STEPS[3].first, true)
        }
        viewModel.channelsRepository.savedChannels.observe(this) {
            if (it.count() > 0) {
                binding.stepView.go(SETUP_STEPS[4].first, true)
                navigateToMainActivity()
            }
        }
    }

    private fun setupListeners() {
        binding.button.setOnClickListener {
            binding.stepView.isVisible = true
            viewModel.validateUrl(binding.editTextTextMultiLine.text.toString())
        }
    }

    private fun styleStepView() {
        binding.stepView.state
            .steps(
                listOf(
                    SETUP_STEPS[0].second,
                    SETUP_STEPS[1].second,
                    SETUP_STEPS[2].second,
                    SETUP_STEPS[3].second,
                    SETUP_STEPS[4].second
                )
            )
            .animationDuration(resources.getInteger(android.R.integer.config_mediumAnimTime))
            .commit()
    }

    private fun navigateToMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    companion object {
        private val SETUP_STEPS = arrayOf(
            0 to "Validate Url",
            1 to "Download content",
            2 to "Extract items",
            3 to "Save items",
            4 to "Display items"
        )
    }
}