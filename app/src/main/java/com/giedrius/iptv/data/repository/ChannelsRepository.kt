package com.giedrius.iptv.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.giedrius.iptv.data.model.Channel
import com.giedrius.iptv.database.channels.ChannelsDao
import com.giedrius.iptv.utils.toChannel
import com.giedrius.iptv.utils.toChannelEntity
import javax.inject.Inject

class ChannelsRepository @Inject constructor(private val channelsDao: ChannelsDao) {

    val savedChannels: LiveData<List<Channel>> = Transformations.map(channelsDao.getSavedChannels()) { it.map { entity -> entity.toChannel() } }

    suspend fun addChannel(channel: Channel) = channelsDao.addChannel(channel.toChannelEntity())

    suspend fun updateChannel(channel: Channel) = channelsDao.updateChannel(channel.toChannelEntity())

    suspend fun deleteChannel(channel: Channel) = channelsDao.deleteChannel(channel.toChannelEntity())

    suspend fun deleteAllChannels() = channelsDao.deleteAllChannels()

    suspend fun uploadChannels(channels: List<Channel>) = channelsDao.uploadChannels(channels.map { it.toChannelEntity() })
}