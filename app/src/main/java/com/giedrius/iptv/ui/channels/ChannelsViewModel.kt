package com.giedrius.iptv.ui.channels

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.giedrius.iptv.data.model.Channel
import com.giedrius.iptv.data.model.Favourite
import com.giedrius.iptv.data.repository.ChannelsRepository
import com.giedrius.iptv.data.repository.DownloadRepository
import com.giedrius.iptv.data.repository.FavouritesRepository
import com.giedrius.iptv.utils.Preferences
import com.giedrius.iptv.utils.SingleLiveEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ChannelsViewModel @ViewModelInject constructor(
    val preferences: Preferences,
    val channelsRepository: ChannelsRepository,
    val favouritesRepository: FavouritesRepository,
    val downloadRepository: DownloadRepository
) : ViewModel() {
    val onFetchedChannels = SingleLiveEvent<List<Channel>>()
    val onDataMissing = MutableLiveData<Boolean>()

    fun performChannelSearch(channels: List<Channel>, phrase: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val filteredPlaylist = channels.filter { it.itemName?.contains(phrase, true) == true }
            onFetchedChannels.postValue(filteredPlaylist)
        }
    }

    fun addFavourite(channel: Channel) {
        viewModelScope.launch(Dispatchers.IO) {
            favouritesRepository.addFavourite(Favourite(channel.id, channel))
        }
    }
}