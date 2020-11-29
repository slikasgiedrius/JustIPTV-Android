package com.giedrius.iptv.data.repository

import androidx.lifecycle.LiveData
import com.giedrius.iptv.data.model.Channel
import com.giedrius.iptv.database.channels.ChannelsDao
import javax.inject.Inject

class ChannelsRepository @Inject constructor(private val channelDao: ChannelsDao) {

    val savedChannels: LiveData<List<Channel>> = channelDao.getSavedChannels()

    suspend fun addChannel(channel: Channel) = channelDao.addChannel(channel)

    suspend fun updateChannel(channel: Channel) = channelDao.updateChannel(channel)

    suspend fun deleteChannel(channel: Channel) = channelDao.deleteChannel(channel)

    suspend fun deleteAllChannels() = channelDao.deleteAllChannels()

    suspend fun uploadChannels(channels: List<Channel>) = channelDao.uploadChannels(channels)
}