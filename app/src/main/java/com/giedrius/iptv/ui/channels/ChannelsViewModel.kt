package com.giedrius.iptv.ui.channels

import android.content.Context
import androidx.hilt.lifecycle.ViewModelInject
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

    val onFetchedChannels = SingleLiveEvent<ArrayList<Channel>>()

    fun saveChannelToDatabase(channel: Channel) {
        viewModelScope.launch(Dispatchers.IO) {
            channelsRepository.addChannel(channel)
        }
    }

    fun deleteAllChannels() {
        viewModelScope.launch(Dispatchers.IO) {
            channelsRepository.deleteAllChannels()
        }
    }

    fun loadChannels(name: String) {
        val parser = PlaylistParser()
        val inputStream = FileInputStream(File(name))
        val playlist = parser.parseFile(inputStream)
        playlist.playlistItems?.let { saveChannels(it) }
    }

    fun loadChannelsNoUpdate(phrase: String?) {
        val parser = PlaylistParser()
        val pathFromPrefs = preferences.getFilePath()
        val inputStream = FileInputStream(File(pathFromPrefs))
        val playlist = parser.parseFile(inputStream)
        val filteredPlaylist = playlist.filterByPhrase(phrase)
        onFetchedChannels.postValue(filteredPlaylist)
    }

    fun detectIfDownloadNeeded(itemsCount: Int) {
        if (itemsCount == 0) {
            channelsDownloader.downloadPlayerFile()
        }
    }

    private fun saveChannels(channels: List<Channel>) {
        viewModelScope.launch(Dispatchers.IO) {
            channelsRepository.uploadChannels(channels)
        }
    }

}