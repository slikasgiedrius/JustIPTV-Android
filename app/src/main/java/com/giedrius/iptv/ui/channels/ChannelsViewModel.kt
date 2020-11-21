package com.giedrius.iptv.ui.channels

import android.content.Context
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.giedrius.iptv.data.model.parser.M3UItem
import com.giedrius.iptv.data.model.parser.M3UParser
import com.giedrius.iptv.utils.Preferences
import com.giedrius.iptv.utils.SingleLiveEvent
import com.giedrius.iptv.utils.extensions.filterByPhrase
import com.lyrebirdstudio.fileboxlib.core.*
import dagger.hilt.android.qualifiers.ApplicationContext
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch
import timber.log.Timber
import java.io.File
import java.io.FileInputStream
import java.util.concurrent.TimeUnit

class ChannelsViewModel @ViewModelInject constructor(
    @ApplicationContext private val application: Context,
    private val preferences: Preferences
) : ViewModel() {

    val onFetchedChannels = SingleLiveEvent<ArrayList<M3UItem>>()

    fun downloadPlayerFile(phrase: String? = null) {
        val initialUrl = preferences.pullString("initial_url").toString()
        val fileBoxRequest = FileBoxRequest(initialUrl)

        val fileBoxConfig = FileBoxConfig.FileBoxConfigBuilder()
            .setCryptoType(CryptoType.NONE)
            .setTTLInMillis(TimeUnit.DAYS.toMillis(7))
            .setDirectory(DirectoryType.CACHE)
            .build()

        viewModelScope.launch {
            FileBoxProvider.newInstance(application, fileBoxConfig)
                .get(fileBoxRequest)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ fileBoxResponse ->
                    when (fileBoxResponse) {
                        is FileBoxResponse.Started -> TODO()
                        is FileBoxResponse.Downloading -> {
                            val progress: Float = fileBoxResponse.progress
                            val ongoingRecord: Record = fileBoxResponse.record
                        }
                        is FileBoxResponse.Complete -> {
                            val savedRecord: Record = fileBoxResponse.record
                            val savedPath = fileBoxResponse.record.getReadableFilePath()

                            preferences.pushString("file_path", savedRecord.decryptedFilePath.toString())
                            savedRecord.decryptedFilePath?.let { loadChannels(it, phrase) }
                        }
                        is FileBoxResponse.Error -> {
                            val savedRecord: Record = fileBoxResponse.record
                            val error = fileBoxResponse.throwable
                        }
                    }
                }, Timber::e)
        }
    }

    fun loadChannels(name: String, phrase: String?) {
        val parser = M3UParser()
        val inputStream = FileInputStream(File(name))
        val playlist = parser.parseFile(inputStream)
        if (phrase.isNullOrEmpty()) {
            onFetchedChannels.postValue(playlist.playlistItems)
        } else {
            val filteredPlaylist = playlist.filterByPhrase(phrase)
            onFetchedChannels.postValue(filteredPlaylist)
        }
    }

    fun loadChannelsNoUpdate(phrase: String?) {
        val parser = M3UParser()
        val pathFromPrefs = preferences.pullString("file_path")
        val inputStream = FileInputStream(File(pathFromPrefs))
        val playlist = parser.parseFile(inputStream)
        val filteredPlaylist = playlist.filterByPhrase(phrase)
        onFetchedChannels.postValue(filteredPlaylist)
    }
}