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
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileInputStream

class ChannelsViewModel @ViewModelInject constructor(
    @ApplicationContext private val application: Context,
    preferences: Preferences,
    val channelsRepository: ChannelsRepository,
    val favouritesRepository: FavouritesRepository
) : ViewModel() {
    var fileDownloader: FileDownloader =
        FileDownloader(application, preferences, this)

    val onFetchedChannels = SingleLiveEvent<List<Channel>>()
    val onProgressChanged = MutableLiveData<Int>()
    val onDataMissing = SingleLiveEvent<Boolean>()

    fun detectIfDownloadNeeded(itemsCount: Int) {
        if (itemsCount == 0) {
            fileDownloader.downloadPlayerFile()
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

    fun addFavourite(channel: Channel) {
        viewModelScope.launch(Dispatchers.IO) {
            favouritesRepository.addFavourite(Favourite(channel.id, channel))
        }
    }

    fun startInputActivity() = onDataMissing.postValue(true)
}