package com.giedrius.iptv.ui.channels

import android.content.Context
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.giedrius.iptv.data.parser.NewM3UItem
import com.giedrius.iptv.room.Channel
import com.giedrius.iptv.room.ChannelRepository
import com.giedrius.iptv.utils.Preferences
import com.giedrius.iptv.utils.SingleLiveEvent
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ChannelsViewModel @ViewModelInject constructor(
    @ApplicationContext private val application: Context,
    private val preferences: Preferences,
    val channelRepository: ChannelRepository
) : ViewModel() {

    var channelsDownloader: ChannelsDownloader = ChannelsDownloader(application, preferences, this)

    val onFetchedChannels = SingleLiveEvent<ArrayList<NewM3UItem>>()

    fun saveChannelToDatabase(channel: NewM3UItem) {
        viewModelScope.launch(Dispatchers.IO) {
            channelRepository.addChannel(
                Channel(
                    id = 0,
                    itemId = channel.itemId,
                    itemName = channel.itemName,
                    itemLogo = channel.itemLogo,
                    itemGroup = channel.itemGroup,
                    itemUrl = channel.itemUrl,
                    itemDuration = ""
                )
            )
        }
    }
}