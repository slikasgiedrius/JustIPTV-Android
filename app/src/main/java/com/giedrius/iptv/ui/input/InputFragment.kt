package com.giedrius.iptv.ui.input

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.giedrius.iptv.R
import com.giedrius.iptv.utils.extensions.toast
import com.google.android.exoplayer2.util.EventLogger
import com.lyrebirdstudio.fileboxlib.core.*
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.input_fragment.*
import java.io.File
import java.io.InputStream
import java.nio.charset.Charset
import java.util.concurrent.TimeUnit


@AndroidEntryPoint
class InputFragment : Fragment(R.layout.input_fragment) {

    private val viewModel: InputViewModel by viewModels()

    @RequiresApi(Build.VERSION_CODES.O)
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

    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("CheckResult")
    private fun downloadIptvFile() {
        val fileBoxRequest = FileBoxRequest("http://uran.iptvboss.net:80/get.php?username=GiedriusSlikas&password=GiedriusSlikas&type=m3u_plus&output=ts")

        val fileBoxConfig = FileBoxConfig.FileBoxConfigBuilder()
            .setCryptoType(CryptoType.NONE)
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

                            Log.d("saved record", savedRecord.toString())
                            Log.d("saved path", savedPath.toString())
                            val fileName = savedRecord.decryptedFilePath
                            val myFile = File(fileName.toString())
                            val ins: InputStream = myFile.inputStream()
                            val content = ins.readBytes().toString(Charset.defaultCharset())
                            textView.text = content.count().toString()
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
