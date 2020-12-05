package com.giedrius.iptv.ui.input

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.giedrius.iptv.BuildConfig
import com.giedrius.iptv.MainActivity
import com.giedrius.iptv.R
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_input.*

@AndroidEntryPoint
class InputActivity : AppCompatActivity(R.layout.activity_input) {

    private val viewModel: InputViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupInitialUrl()
        handleObservers()
        setupListeners()
    }

    private fun setupInitialUrl() {
        val initialUrl = viewModel.preferences.getInitialUrl()
        if (initialUrl == null) {
            editTextTextMultiLine.setText(BuildConfig.MY_URL)
        } else {
            viewModel.validateUrl(editTextTextMultiLine.text.toString())
        }
    }

    private fun handleObservers() {
        viewModel.onUrlIsValid.observe(this) {
            navigateToChannels()
        }
    }

    private fun setupListeners() {
        button.setOnClickListener {
            viewModel.validateUrl(editTextTextMultiLine.text.toString())
        }
    }

    private fun navigateToChannels() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}