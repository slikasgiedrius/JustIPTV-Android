package com.giedrius.iptv.room

import androidx.lifecycle.LiveData
import com.giedrius.iptv.data.model.Channel
import javax.inject.Inject

class ChannelRepository @Inject constructor(private val channelDao: ChannelDao) {

    val savedChannels: LiveData<List<Channel>> = channelDao.getSavedChannels()

    suspend fun addChannel(channel: Channel) = channelDao.addChannel(channel)

    suspend fun updateChannel(channel: Channel) = channelDao.updateChannel(channel)

    suspend fun deleteUser(channel: Channel) = channelDao.deleteUser(channel)

    suspend fun deleteAllUsers() = channelDao.deleteAllChannels()

    suspend fun uploadChannels(channels: List<Channel>) = channelDao.uploadChannels(channels)
}