package com.giedrius.iptv

import android.app.Application
import android.content.Context
import com.cioccarellia.ksprefs.KsPrefs
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber
import timber.log.Timber.DebugTree


@HiltAndroidApp
class IptvApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        appContext = this
        if (BuildConfig.DEBUG) {
            Timber.plant(DebugTree())
        }
    }

    companion object {
        const val FILE_PATH = "file_path"
        lateinit var appContext: Context
        val prefs by lazy { KsPrefs(appContext) }
    }
}
