package com.giedrius.iptv.utils

import android.content.Context
import com.jcloquell.androidsecurestorage.SecureStorage

class Preferences(context: Context) {
    private val secureStorage = SecureStorage(context)

    private fun save(key: String, value: Any) = secureStorage.storeObject(key, value)

    private fun getString(key: String): String? {
        return secureStorage.getObject(key, String::class.java)
    }

    //Public methods to be used in the project

    fun setInitialUrl(url: String) = this.save(INITIAL_URL, url)

    fun getInitialUrl(): String? = this.getString(INITIAL_URL)

    fun setFilePath(path: String) = this.save(FILE_PATH, path)

    fun getFilePath(): String? = this.getString(FILE_PATH)

    companion object {
        private const val INITIAL_URL = "initial_url"
        private const val FILE_PATH = "file_path"
    }
}