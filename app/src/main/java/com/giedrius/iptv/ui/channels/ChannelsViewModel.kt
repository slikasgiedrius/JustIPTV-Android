package com.giedrius.iptv.ui.channels

import android.content.Context
import android.content.Intent
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.giedrius.iptv.MainActivity
import com.giedrius.iptv.data.model.Channel
import com.giedrius.iptv.data.model.Favourite
import com.giedrius.iptv.data.repository.ChannelsRepository
import com.giedrius.iptv.data.repository.FavouritesRepository
import com.giedrius.iptv.utils.PlaylistParser
import com.giedrius.iptv.utils.Preferences
import com.giedrius.iptv.utils.SingleLiveEvent
import com.lyrebirdstudio.fileboxlib.core.*
import dagger.hilt.android.qualifiers.ApplicationContext
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber
import java.io.File
import java.io.FileInputStream
import java.util.concurrent.TimeUnit
import kotlin.math.ceil

class ChannelsViewModel @ViewModelInject constructor(
    @ApplicationContext private val application: Context,
    val preferences: Preferences,
    val channelsRepository: ChannelsRepository,
    val favouritesRepository: FavouritesRepository
) : ViewModel() {
    val onFetchedChannels = SingleLiveEvent<List<Channel>>()
    val onProgressChanged = MutableLiveData<Int>()
    val onDataMissing = SingleLiveEvent<Boolean>()

    fun detectIfDownloadNeeded(itemsCount: Int) {
        if (itemsCount == 0) downloadFile()
    }

    private fun downloadFile() {
        val initialUrl = preferences.getInitialUrl()
        if (initialUrl == null) onDataMissing.postValue(true)
        val fileBoxRequest = initialUrl?.let { FileBoxRequest(it) }

        val fileBoxConfig = FileBoxConfig.FileBoxConfigBuilder()
            .setCryptoType(CryptoType.NONE)
            .setTTLInMillis(TimeUnit.DAYS.toMillis(7))
            .setDirectory(DirectoryType.CACHE)
            .build()

        fileBoxRequest?.let {
            FileBoxProvider.newInstance(application, fileBoxConfig)
                .get(it)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ fileBoxResponse ->
                    when (fileBoxResponse) {
                        is FileBoxResponse.Started -> Timber.d("File download started")
                        is FileBoxResponse.Downloading -> displayProgress(fileBoxResponse.progress)
                        is FileBoxResponse.Complete -> fileBoxResponse.record.decryptedFilePath?.let { it -> loadChannels(it) }
                        is FileBoxResponse.Error -> Timber.e("Error while downloading file ${fileBoxResponse.throwable}")
                    }
                }, Timber::e)
        }
    }

    private fun loadChannels(name: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val parser = PlaylistParser()
            val inputStream = FileInputStream(File(name))
            val playlist = parser.parseFile(inputStream)
            playlist.playlistItems?.let { saveChannels(it) }
        }
    }

    fun performChannelSearch(channels: List<Channel>, phrase: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val filteredPlaylist = channels.filter { it.itemName?.contains(phrase, true) == true }
            onFetchedChannels.postValue(filteredPlaylist)
        }
    }

    private fun saveChannels(channels: List<Channel>) {
        viewModelScope.launch(Dispatchers.IO) {
            channelsRepository.uploadChannels(channels)
        }
    }


    fun addFavourite(channel: Channel) {
        viewModelScope.launch(Dispatchers.IO) {
            favouritesRepository.addFavourite(Favourite(channel.id, channel))
        }
    }

    private fun displayProgress(progress: Float) {
        val percent = ceil((progress) * 100).toInt()
        onProgressChanged.postValue(percent)
    }
}