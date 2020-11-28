package com.giedrius.iptv.ui.channels

import android.content.Context
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.giedrius.iptv.data.parser.NewM3UItem
import com.giedrius.iptv.room.ChannelRepository
import com.giedrius.iptv.utils.Preferences
import com.giedrius.iptv.utils.SingleLiveEvent
import dagger.hilt.android.qualifiers.ApplicationContext

class ChannelsViewModel @ViewModelInject constructor(
    @ApplicationContext private val application: Context,
    preferences: Preferences,
    channelRepository: ChannelRepository
) : ViewModel() {

    var channelsDownloader: ChannelsDownloader = ChannelsDownloader(application, preferences, this, channelRepository)

    val onFetchedChannels = SingleLiveEvent<ArrayList<NewM3UItem>>()
}