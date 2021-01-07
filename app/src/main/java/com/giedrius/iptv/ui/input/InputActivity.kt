package com.giedrius.iptv.ui.input

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.giedrius.iptv.BuildConfig
import com.giedrius.iptv.R
import com.giedrius.iptv.databinding.ActivityInputBinding
import com.giedrius.iptv.ui.introduction.IntroductionActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class InputActivity : AppCompatActivity(R.layout.activity_input) {

    private lateinit var binding: ActivityInputBinding

    private val viewModel: InputViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInputBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        setupInitialUrl()
        handleObservers()
        setupListeners()
    }

    private fun setupInitialUrl() {
        val initialUrl = viewModel.preferences.getInitialUrl()
        if (initialUrl == null) {
            binding.editTextTextMultiLine.setText(BuildConfig.MY_URL)
        } else {
            viewModel.validateUrl(binding.editTextTextMultiLine.text.toString())
        }
    }

    private fun handleObservers() {
        viewModel.onUrlIsValid.observe(this) { navigateToIntroductionActivity() }
    }

    private fun setupListeners() {
        binding.button.setOnClickListener {
            viewModel.validateUrl(binding.editTextTextMultiLine.text.toString())
        }
    }

    private fun navigateToIntroductionActivity() {
        val intent = Intent(this, IntroductionActivity::class.java)
        startActivity(intent)
        finish()
    }
}