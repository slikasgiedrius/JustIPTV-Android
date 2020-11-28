package com.giedrius.iptv.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.giedrius.iptv.data.parser.NewM3UItem

@Database(entities = [NewM3UItem::class], version = 1, exportSchema = false)
abstract class ChannelDatabase : RoomDatabase() {

    abstract fun channelDao(): ChannelDao

    companion object {
        @Volatile
        private var INSTANCE: ChannelDatabase? = null

        fun getDatabase(context: Context): ChannelDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ChannelDatabase::class.java,
                    "channel_table"
                ).build()
                INSTANCE = instance
                return instance
            }

        }
    }
}