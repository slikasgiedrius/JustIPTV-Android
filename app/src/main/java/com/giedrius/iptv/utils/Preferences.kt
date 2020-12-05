package com.giedrius.iptv.utils

import android.content.Context
import android.content.SharedPreferences

class Preferences(context: Context) {
    private val sharedPref: SharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    private fun saveString(key: String, value: String) {
        val editor: SharedPreferences.Editor = sharedPref.edit()
        editor.putString(key, value)
        editor.apply()
    }

    private fun getString(key: String): String? {
        return sharedPref.getString(key, null)
    }

    fun clearSharedPreference() {
        val editor: SharedPreferences.Editor = sharedPref.edit()
        editor.clear()
        editor.apply()
    }

    fun setInitialUrl(url: String) = this.saveString(INITIAL_URL, url)
    fun getInitialUrl(): String? = this.getString(INITIAL_URL)

    companion object {
        private const val PREFS_NAME = "just_iptv"
        private const val INITIAL_URL = "initial_url"
    }
}