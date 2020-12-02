package com.giedrius.iptv.ui.channels

import android.content.Context
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.giedrius.iptv.data.model.Channel
import com.giedrius.iptv.data.repository.ChannelsRepository
import com.giedrius.iptv.utils.PlaylistParser
import com.giedrius.iptv.utils.Preferences
import com.giedrius.iptv.utils.SingleLiveEvent
import com.giedrius.iptv.utils.extensions.filterByPhrase
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber
import java.io.File
import java.io.FileInputStream

class ChannelsViewModel @ViewModelInject constructor(
    @ApplicationContext private val application: Context,
    private val preferences: Preferences,
    val channelsRepository: ChannelsRepository
) : ViewModel() {
    var channelsDownloader: ChannelsDownloader = ChannelsDownloader(application, preferences, this)

    val onFetchedChannels = SingleLiveEvent<List<Channel>>()
    val onProgressChanged = MutableLiveData<Int>()

    fun detectIfDownloadNeeded(itemsCount: Int) {
        if (itemsCount == 0) {
            channelsDownloader.downloadPlayerFile()
        }
    }

    fun loadChannels(name: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val parser = PlaylistParser()
            val inputStream = FileInputStream(File(name))
            val playlist = parser.parseFile(inputStream)
            playlist.playlistItems?.let { saveChannels(it) }
        }
    }

    fun loadChannelsNoUpdate(channels: List<Channel>, phrase: String) {
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

    fun downloadProgressChanged(progress: Int) = onProgressChanged.postValue(progress)

    fun deleteAllChannels() {
        viewModelScope.launch(Dispatchers.IO) {
            channelsRepository.deleteAllChannels()
        }
    }
}