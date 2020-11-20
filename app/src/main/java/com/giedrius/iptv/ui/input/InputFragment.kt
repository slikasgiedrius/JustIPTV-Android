package com.giedrius.iptv.ui.input

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.giedrius.iptv.R
import com.giedrius.iptv.data.model.Channel
import com.giedrius.iptv.parser.M3UParser
import com.giedrius.iptv.utils.extensions.toast
import com.iheartradio.m3u8.*
import com.iheartradio.m3u8.data.Playlist
import com.lyrebirdstudio.fileboxlib.core.*
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.input_fragment.*
import timber.log.Timber
import java.io.File
import java.io.FileInputStream
import java.io.InputStream
import java.io.OutputStream
import java.nio.charset.Charset
import java.util.concurrent.TimeUnit


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

    private fun handleObservers() {
        viewModel.onUrlIsValid.observe(viewLifecycleOwner) {
            context?.toast("$it is valid url!")
            val action = InputFragmentDirections.actionInputFragmentToChannelsFragment(
                editTextTextMultiLine.text.toString()
            )
            view?.findNavController()?.navigate(action)
        }

        viewModel.onUrlIsInvalid.observe(viewLifecycleOwner) {
            it.message?.let { message -> context?.toast(message) }
        }
    }

    private fun setupListeners() {
        button.setOnClickListener {
            viewModel.validateUrl(editTextTextMultiLine.text.toString())
        }
    }

    private fun setupInitialUrl() {
        editTextTextMultiLine.setText("http://uran.iptvboss.net:80/get.php?username=GiedriusSlikas&password=GiedriusSlikas&type=m3u_plus&output=ts")
    }
}
