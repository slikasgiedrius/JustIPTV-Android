package com.giedrius.iptv.utils

import android.content.Context
import com.jcloquell.androidsecurestorage.SecureStorage

class Preferences(private val context: Context) {
    val secureStorage = SecureStorage(context)

    fun pushString(key: String, value: String) {
        secureStorage.storeObject(key, value)
    }

    fun pullString(key: String): String? {
        return secureStorage.getObject(key, String::class.java)
    }
}