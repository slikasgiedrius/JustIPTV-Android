package com.giedrius.iptv.utils

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences

class Preferences(context: Context) {
    private val sharedPref: SharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    @SuppressLint("CommitPrefEdits")
    private fun saveStringValue(KEY_NAME: String, text: String) {
        val editor: SharedPreferences.Editor = sharedPref.edit()
        editor.putString(KEY_NAME, text)
        editor.apply()
    }

    private fun getValueString(KEY_NAME: String): String? {
        return sharedPref.getString(KEY_NAME, null)
    }


    fun clearSharedPreference() {
        val editor: SharedPreferences.Editor = sharedPref.edit()

        editor.clear()
        editor.apply()
    }

    //Public methods to be used in the project

    fun setInitialUrl(url: String) = this.saveStringValue(INITIAL_URL, url)

    fun getInitialUrl(): String? = this.getValueString(INITIAL_URL)

    companion object {
        private const val PREFS_NAME = "justiptv"
        private const val INITIAL_URL = "initial_url"
    }
}