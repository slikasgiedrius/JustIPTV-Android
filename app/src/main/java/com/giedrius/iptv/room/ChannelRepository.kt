package com.giedrius.iptv.room

import androidx.lifecycle.LiveData
import javax.inject.Inject

class ChannelRepository @Inject constructor(private val channelDao: ChannelDao){

    val readAllData: LiveData<List<Channel>> = channelDao.readAllData()

    suspend fun addChannel(channel: Channel) {
        channelDao.addChannel(channel)
    }
}