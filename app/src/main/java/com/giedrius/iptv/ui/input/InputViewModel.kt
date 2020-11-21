package com.giedrius.iptv.ui.input

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.giedrius.iptv.utils.Preferences
import com.giedrius.iptv.utils.SingleLiveEvent

class InputViewModel @ViewModelInject constructor(
    private val preferences: Preferences
) : ViewModel() {

    val onUrlIsValid = SingleLiveEvent<String>()

    fun validateUrl(url: String) {
        onUrlIsValid.invoke(url)
        saveInitialUrl(url)
    }

    private fun saveInitialUrl(url: String) = preferences.pushString("initial_url", url)

    fun getInitialUrl(): String? = preferences.pullString("initial_url")
}
