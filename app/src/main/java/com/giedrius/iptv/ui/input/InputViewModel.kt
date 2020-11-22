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
        onUrlIsValid.invoke(url)
        //TODO uncomment once validation is done
//        preferences.setInitialUrl(url)
    }
}
