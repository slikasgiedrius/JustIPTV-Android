package com.giedrius.iptv.di

import android.content.Context
import com.giedrius.iptv.room.ChannelDatabase
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
    fun provideChannelDao(@ApplicationContext appContext: Context) = ChannelDatabase.getDatabase(appContext).channelDao()
}