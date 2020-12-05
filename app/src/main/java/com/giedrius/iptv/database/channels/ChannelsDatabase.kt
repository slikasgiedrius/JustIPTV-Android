package com.giedrius.iptv.database.channels

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.giedrius.iptv.data.entity.ChannelEntity

@Database(entities = [ChannelEntity::class], version = 1, exportSchema = false)
abstract class ChannelsDatabase : RoomDatabase() {

    abstract fun channelsDao(): ChannelsDao

    companion object {
        @Volatile
        private var INSTANCE: ChannelsDatabase? = null

        fun getDatabase(context: Context): ChannelsDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ChannelsDatabase::class.java,
                    "channels_table"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}