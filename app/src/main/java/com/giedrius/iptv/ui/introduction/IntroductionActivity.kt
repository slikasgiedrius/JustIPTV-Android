package com.giedrius.iptv.ui.introduction

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.giedrius.iptv.R
import com.giedrius.iptv.ui.channels.ChannelsViewModel
import kotlinx.android.synthetic.main.activity_introduction.*

class IntroductionActivity : AppCompatActivity(R.layout.activity_introduction) {

    private val viewModel: IntroductionViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        handleObservers()
    }

    private fun handleObservers() {
    }
}