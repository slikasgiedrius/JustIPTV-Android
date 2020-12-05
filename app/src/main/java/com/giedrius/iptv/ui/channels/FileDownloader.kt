package com.giedrius.iptv.ui.channels

import android.content.Context
import androidx.lifecycle.viewModelScope
import com.giedrius.iptv.utils.Preferences
import com.lyrebirdstudio.fileboxlib.core.*
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import kotlin.math.ceil

class FileDownloader @Inject constructor(
    private val context: Context,
    private val preferences: Preferences,
    private val viewModel: ChannelsViewModel
) {

    fun downloadPlayerFile() {
        val initialUrl = preferences.getInitialUrl()
        val fileBoxRequest = initialUrl?.let { FileBoxRequest(it) }

        val fileBoxConfig = FileBoxConfig.FileBoxConfigBuilder()
            .setCryptoType(CryptoType.NONE)
            .setTTLInMillis(TimeUnit.DAYS.toMillis(7))
            .setDirectory(DirectoryType.CACHE)
            .build()

        viewModel.viewModelScope.launch(Dispatchers.IO) {
            fileBoxRequest?.let {
                FileBoxProvider.newInstance(context, fileBoxConfig)
                    .get(it)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({ fileBoxResponse ->
                        when (fileBoxResponse) {
                            is FileBoxResponse.Started -> Timber.d("File download started")
                            is FileBoxResponse.Downloading -> displayProgress(fileBoxResponse.progress)
                            is FileBoxResponse.Complete -> fileBoxResponse.record.decryptedFilePath?.let { it -> viewModel.loadChannels(it) }
                            is FileBoxResponse.Error -> Timber.e("Error while downloading file ${fileBoxResponse.throwable}")
                        }
                    }, Timber::e)
            }
        }
    }

    private fun displayProgress(progress: Float) {
        val percent = ceil((progress) * 100).toInt()
        Timber.d("CHANNELS DOWNLOADING $percent")
        viewModel.downloadProgressChanged(percent)
    }
}