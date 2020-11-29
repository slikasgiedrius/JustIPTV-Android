package com.giedrius.iptv.database.channels

import androidx.lifecycle.LiveData
import androidx.room.*
import com.giedrius.iptv.data.model.Channel

@Dao
interface ChannelsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addChannel(channel: Channel)

    @Update
    suspend fun updateChannel(channel: Channel)

    @Delete
    suspend fun deleteChannel(channel: Channel)

    @Query("DELETE FROM channels_table")
    suspend fun deleteAllChannels()

    @Query("SELECT * FROM channels_table ORDER BY id ASC")
    fun getSavedChannels(): LiveData<List<Channel>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun uploadChannels(channels: List<Channel>)
}