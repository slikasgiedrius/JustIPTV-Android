package com.giedrius.iptv.di

import android.content.Context
import com.giedrius.iptv.utils.Preferences
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object ApplicationModule {

    @Singleton
    @Provides
    fun providePreferences(@ApplicationContext appContext: Context) = Preferences(appContext)

    @Singleton
    @Provides
    fun provideDatabaseReference() = Firebase.database.reference
}