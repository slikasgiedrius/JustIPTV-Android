package com.giedrius.iptv.utils

import android.content.Context
import com.giedrius.iptv.data.parser.NewM3UPlaylist
import com.jcloquell.androidsecurestorage.SecureStorage

class Preferences(context: Context) {
    private val secureStorage = SecureStorage(context)

    private fun save(key: String, value: Any) = secureStorage.storeObject(key, value)

    private fun getString(key: String): String? {
        return secureStorage.getObject(key, String::class.java)
    }

    private fun getM3UPlaylist(): NewM3UPlaylist? {
        return secureStorage.getObject(PLAYLIST, NewM3UPlaylist::class.java)
    }

    //Public methods to be used in the project

    fun setInitialUrl(url: String) = this.save(INITIAL_URL, url)

    fun getInitialUrl(): String? = this.getString(INITIAL_URL)

    fun setFilePath(path: String) = this.save(FILE_PATH, path)

    fun getFilePath(): String? = this.getString(FILE_PATH)

    fun setPlaylist(playlist: NewM3UPlaylist) = this.save(PLAYLIST, playlist)

    fun getPlaylist(): NewM3UPlaylist? = this.getM3UPlaylist()

    companion object {
        private const val INITIAL_URL = "initial_url"
        private const val FILE_PATH = "file_path"
        private const val PLAYLIST = "playlist"
    }
}