package com.giedrius.iptv.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.giedrius.iptv.data.parser.NewM3UItem

@Dao
interface ChannelDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addChannel(channel: NewM3UItem)

    @Update
    suspend fun updateChannel(channel: NewM3UItem)

    @Delete
    suspend fun deleteUser(channel: NewM3UItem)

    @Query("DELETE FROM channel_table")
    suspend fun deleteAllChannels()

    @Query("SELECT * FROM channel_table ORDER BY itemUrl ASC")
    fun readAllData(): LiveData<List<NewM3UItem>>
}