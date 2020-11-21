package com.giedrius.iptv.ui.input

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.giedrius.iptv.R
import com.giedrius.iptv.utils.Preferences
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.input_fragment.*
import timber.log.Timber
import javax.inject.Inject


@AndroidEntryPoint
class InputFragment : Fragment(R.layout.input_fragment) {

    private val viewModel: InputViewModel by viewModels()

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)

        setupInitialUrl()
        handleObservers()
        setupListeners()
    }

    private fun setupInitialUrl() {
        val initialUrl = viewModel.getInitialUrl()
        Timber.d("INITIAL URL $initialUrl")
        if (initialUrl.isNullOrEmpty()) {
            editTextTextMultiLine.setText(
                "http://uran.iptvboss.net:80/get.php?username=GiedriusSlikas&password=GiedriusSlikas&type=m3u_plus&output=ts"
            )
        }
    }

    private fun handleObservers() {
        viewModel.onUrlIsValid.observe(viewLifecycleOwner) { navigateToChannels() }
    }

    private fun setupListeners() {
        button.setOnClickListener {
            viewModel.validateUrl(editTextTextMultiLine.text.toString())
        }
    }

    private fun navigateToChannels() = findNavController().navigate(R.id.channelsFragment)

}
