package com.giedrius.iptv.di

import android.content.Context
import com.giedrius.iptv.ui.channels.ChannelsDownloader
import com.giedrius.iptv.ui.channels.ChannelsViewModel
import com.giedrius.iptv.utils.Preferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object ChannelsModule {

    @Singleton
    @Provides
    fun provideChannelsDownloader(
        @ApplicationContext appContext: Context,
        preferences: Preferences,
        channelsViewModel: ChannelsViewModel
    ) = ChannelsDownloader(
            appContext,
            preferences,
            channelsViewModel
    )

    @Singleton
    @Provides
    fun provideChannelsViewModel(
        @ApplicationContext appContext: Context,
        preferences: Preferences
    ) = ChannelsViewModel(appContext, preferences)
}