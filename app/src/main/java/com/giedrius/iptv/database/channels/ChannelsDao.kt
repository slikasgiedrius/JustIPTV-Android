package com.giedrius.iptv.database.channels

import androidx.lifecycle.LiveData
import androidx.room.*
import com.giedrius.iptv.data.entity.ChannelEntity
import com.giedrius.iptv.data.model.Channel

@Dao
interface ChannelsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addChannel(channel: ChannelEntity)

    @Update
    suspend fun updateChannel(channel: ChannelEntity)

    @Delete
    suspend fun deleteChannel(channel: ChannelEntity)

    @Query("DELETE FROM channels_table")
    suspend fun deleteAllChannels()

    @Query("SELECT * FROM channels_table ORDER BY id ASC")
    fun getSavedChannels(): LiveData<List<ChannelEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun uploadChannels(channels: List<ChannelEntity>)
}