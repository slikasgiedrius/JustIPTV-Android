package com.giedrius.iptv.ui.channels

import android.content.Context
import androidx.lifecycle.viewModelScope
import com.giedrius.iptv.data.model.parser.NewM3UParser
import com.giedrius.iptv.utils.Preferences
import com.giedrius.iptv.utils.extensions.filterByPhrase
import com.lyrebirdstudio.fileboxlib.core.*
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch
import timber.log.Timber
import java.io.File
import java.io.FileInputStream
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class ChannelsDownloader @Inject constructor(
    private val context: Context,
    private val preferences: Preferences,
    private val viewModel: ChannelsViewModel
){

    fun downloadPlayerFile() {
        val initialUrl = preferences.getInitialUrl()
        val fileBoxRequest = initialUrl?.let { FileBoxRequest(it) }

        val fileBoxConfig = FileBoxConfig.FileBoxConfigBuilder()
            .setCryptoType(CryptoType.NONE)
            .setTTLInMillis(TimeUnit.DAYS.toMillis(7))
            .setDirectory(DirectoryType.CACHE)
            .build()

        viewModel.viewModelScope.launch {
            fileBoxRequest?.let {
                FileBoxProvider.newInstance(context, fileBoxConfig)
                    .get(it)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({ fileBoxResponse ->
                        when (fileBoxResponse) {
                            is FileBoxResponse.Downloading -> {
                                val progress: Float = fileBoxResponse.progress
                                val ongoingRecord: Record = fileBoxResponse.record
                            }
                            is FileBoxResponse.Complete -> {
                                val savedRecord: Record = fileBoxResponse.record
                                val savedPath = fileBoxResponse.record.getReadableFilePath()

                                preferences.setFilePath(savedRecord.decryptedFilePath.toString())
                                savedRecord.decryptedFilePath?.let { it -> loadChannels(it) }
                            }
                            is FileBoxResponse.Error -> {
                                val savedRecord: Record = fileBoxResponse.record
                                val error = fileBoxResponse.throwable
                            }
                        }
                    }, Timber::e)
            }
        }
    }

    fun loadChannels(name: String) {
        val parser = NewM3UParser()
        val inputStream = FileInputStream(File(name))
        val playlist = parser.parseFile(inputStream)
        viewModel.onFetchedChannels.postValue(playlist.playlistItems)
    }

    fun loadChannelsNoUpdate(phrase: String?) {
        val parser = NewM3UParser()
        val pathFromPrefs = preferences.getFilePath()
        val inputStream = FileInputStream(File(pathFromPrefs))
        val playlist = parser.parseFile(inputStream)
        val filteredPlaylist = playlist.filterByPhrase(phrase)
        viewModel.onFetchedChannels.postValue(filteredPlaylist)
    }
}