package com.giedrius.iptv.database.favourites

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.giedrius.iptv.data.entity.FavouriteEntity

@Database(entities = [FavouriteEntity::class], version = 1, exportSchema = false)
abstract class  FavouritesDatabase : RoomDatabase() {

    abstract fun favouritesDao(): FavouritesDao

    companion object {
        @Volatile
        private var INSTANCE: FavouritesDatabase? = null

        fun getDatabase(context: Context): FavouritesDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    FavouritesDatabase::class.java,
                    "favourites_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}