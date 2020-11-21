package com.giedrius.iptv.ui.channels

import android.content.Context
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.giedrius.iptv.data.model.parser.M3UItem
import com.giedrius.iptv.data.model.parser.M3UParser
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
    @ApplicationContext private val application: Context
) : ViewModel() {

    val onFetchedChannels = MutableLiveData<ArrayList<M3UItem>>()
    private var path: String = ""

    fun downloadFile(url: String) {
        val fileBoxRequest = FileBoxRequest(url)

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
                .subscribe ({ fileBoxResponse ->
                    when (fileBoxResponse) {
                        is FileBoxResponse.Started -> TODO()
                        is FileBoxResponse.Downloading -> {
                            val progress: Float = fileBoxResponse.progress
                            val ongoingRecord: Record = fileBoxResponse.record
                        }
                        is FileBoxResponse.Complete -> {
                            val savedRecord: Record = fileBoxResponse.record
                            val savedPath = fileBoxResponse.record.getReadableFilePath()

                            path = savedRecord.decryptedFilePath.toString()
                            savedRecord.decryptedFilePath?.let { loadChannels(it) }
                        }
                        is FileBoxResponse.Error -> {
                            val savedRecord: Record = fileBoxResponse.record
                            val error = fileBoxResponse.throwable
                        }
                    }
                }, Timber::e)
        }
    }

    fun loadChannels(name: String = "", phrase: String? = null) {
        val parser = M3UParser()
        val inputStream = FileInputStream(File(path))
        val playlist = parser.parseFile(inputStream)
        if (phrase.isNullOrEmpty()) {
            onFetchedChannels.postValue(playlist.playlistItems)
        } else {
            val filteredPlaylist = playlist.filterByPhrase(phrase)
            onFetchedChannels.postValue(filteredPlaylist)
        }
    }
}
