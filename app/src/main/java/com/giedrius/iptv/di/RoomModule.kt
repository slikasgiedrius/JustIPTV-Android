package com.giedrius.iptv.di

import android.content.Context
import androidx.room.Room
import com.giedrius.iptv.room.DataDao
import com.giedrius.iptv.room.DataDatabase
import com.giedrius.iptv.room.DataEntity
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object RoomModule {

    @Singleton
    @Provides
    fun provideDataDb(@ApplicationContext context: Context): DataDatabase{
        return Room.databaseBuilder(
            context,
            DataDatabase::class.java,
            DataDatabase.DATABASE_NAME,
        ).fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun provideDataDAO(database: DataDatabase): DataDao {
        return database.dataDao()
    }
}