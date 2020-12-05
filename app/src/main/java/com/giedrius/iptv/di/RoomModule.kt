package com.giedrius.iptv.di

import android.content.Context
import com.giedrius.iptv.database.channels.ChannelsDatabase
import com.giedrius.iptv.database.favourites.FavouritesDatabase
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
    fun provideChannelDao(@ApplicationContext appContext: Context) = ChannelsDatabase.getDatabase(appContext).channelsDao()

    @Singleton
    @Provides
    fun provideFavouriteDao(@ApplicationContext appContext: Context) = FavouritesDatabase.getDatabase(appContext).favouritesDao()
}