package com.giedrius.iptv

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class IptvApplication : Application() {

    override fun onCreate() {
        super.onCreate()
    }
}