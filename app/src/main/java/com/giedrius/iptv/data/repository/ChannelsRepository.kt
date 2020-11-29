package com.giedrius.iptv.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.map
import com.giedrius.iptv.data.entity.ChannelEntity
import com.giedrius.iptv.data.model.Channel
import com.giedrius.iptv.database.channels.ChannelsDao
import com.giedrius.iptv.utils.toChannel
import com.giedrius.iptv.utils.toChannelEntity
import javax.inject.Inject

class ChannelsRepository @Inject constructor(private val channelDao: ChannelsDao) {

    val savedChannels: LiveData<List<Channel>> = Transformations.map(channelDao.getSavedChannels()) { it.map { entity -> entity.toChannel() } }

    suspend fun addChannel(channel: Channel) = channelDao.addChannel(channel.toChannelEntity())

    suspend fun updateChannel(channel: Channel) = channelDao.updateChannel(channel.toChannelEntity())

    suspend fun deleteChannel(channel: Channel) = channelDao.deleteChannel(channel.toChannelEntity())

    suspend fun deleteAllChannels() = channelDao.deleteAllChannels()

    suspend fun uploadChannels(channels: List<Channel>) = channelDao.uploadChannels(channels.map { it.toChannelEntity() })
}