package com.giedrius.iptv.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ChannelDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addChannel(channel: Channel)

    @Query("SELECT * FROM channel_table ORDER BY id ASC")
    fun readAllData(): LiveData<List<Channel>>
}