package com.giedrius.iptv.room

import androidx.lifecycle.LiveData
import com.giedrius.iptv.data.parser.NewM3UItem
import javax.inject.Inject

class ChannelRepository @Inject constructor(private val channelDao: ChannelDao) {

    val readAllData: LiveData<List<NewM3UItem>> = channelDao.readAllData()

    suspend fun addChannel(channel: NewM3UItem) = channelDao.addChannel(channel)

    suspend fun updateChannel(channel: NewM3UItem) = channelDao.updateChannel(channel)

    suspend fun deleteUser(channel: NewM3UItem) = channelDao.deleteUser(channel)

    suspend fun deleteAllUsers() = channelDao.deleteAllChannels()
}