package com.giedrius.iptv.ui.input

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.giedrius.iptv.R
import com.giedrius.iptv.utils.extensions.toast
import com.lyrebirdstudio.fileboxlib.core.*
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.input_fragment.*
import java.util.concurrent.TimeUnit

@AndroidEntryPoint
class InputFragment : Fragment(R.layout.input_fragment) {

    private val viewModel: InputViewModel by viewModels()

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)

        handleObservers()
        setupListeners()
        downloadIptvFile()
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

    @SuppressLint("CheckResult")
    private fun downloadIptvFile() {
        val fileBoxRequest = FileBoxRequest("http://uran.iptvboss.net:80/get.php?username=GiedriusSlikas&password=GiedriusSlikas&type=m3u_plus&output=ts")

        val fileBoxConfig = FileBoxConfig.FileBoxConfigBuilder()
            .setCryptoType(CryptoType.CONCEAL)
            .setTTLInMillis(TimeUnit.DAYS.toMillis(7))
            .setDirectory(DirectoryType.CACHE)
            .build()

        context?.let {
            FileBoxProvider.newInstance(it, fileBoxConfig)
                .get(fileBoxRequest)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { fileBoxResponse ->
                    when (fileBoxResponse) {
                        is FileBoxResponse.Downloading -> {
                            val progress: Float = fileBoxResponse.progress
                            val ongoingRecord: Record = fileBoxResponse.record
                        }
                        is FileBoxResponse.Complete -> {
                            val savedRecord: Record = fileBoxResponse.record
                            val savedPath = fileBoxResponse.record.getReadableFilePath()
                        }
                        is FileBoxResponse.Error -> {
                            val savedRecord: Record = fileBoxResponse.record
                            val error = fileBoxResponse.throwable
                        }
                    }
                }
        }
    }
}
