package com.giedrius.iptv.ui.input

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.giedrius.iptv.utils.Preferences
import com.giedrius.iptv.utils.SingleLiveEvent

class InputViewModel @ViewModelInject constructor(
    val preferences: Preferences
) : ViewModel() {

    val onUrlIsValid = SingleLiveEvent<String>()

    fun validateUrl(url: String) {
        url.apply {
            saveUrlIfNeeded(this)
            onUrlIsValid.invoke(this)
        }
    }

    private fun saveUrlIfNeeded(url: String) {
        if (preferences.getInitialUrl() == null){
            preferences.setInitialUrl(url)
        }
    }
}
