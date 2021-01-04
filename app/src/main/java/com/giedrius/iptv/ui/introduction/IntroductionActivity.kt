package com.giedrius.iptv.ui.introduction

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.giedrius.iptv.R
import com.giedrius.iptv.databinding.ActivityIntroductionBinding
import com.giedrius.iptv.ui.channels.ChannelsViewModel
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

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
    }
}