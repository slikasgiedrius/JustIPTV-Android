package com.giedrius.iptv.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.giedrius.iptv.data.model.Channel

@Dao
interface ChannelDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addChannel(channel: Channel)

    @Update
    suspend fun updateChannel(channel: Channel)

    @Delete
    suspend fun deleteUser(channel: Channel)

    @Query("DELETE FROM channel_table")
    suspend fun deleteAllChannels()

    @Query("SELECT * FROM channel_table ORDER BY itemUrl ASC")
    fun readAllData(): LiveData<List<Channel>>
}