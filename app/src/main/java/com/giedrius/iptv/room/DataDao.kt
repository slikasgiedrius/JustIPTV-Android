package com.giedrius.iptv.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface DataDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(dataEntity: DataEntity): Long

    @Query("SELECT * FROM data")
    suspend fun get(): List<DataEntity>
}