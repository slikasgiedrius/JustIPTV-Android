package com.giedrius.iptv.ui.input

import android.content.Context
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.giedrius.iptv.data.repository.MainRepository
import com.giedrius.iptv.utils.SingleLiveEvent
import com.google.firebase.database.DatabaseReference
import dagger.hilt.android.qualifiers.ApplicationContext

class InputViewModel @ViewModelInject constructor(
    @ApplicationContext private val application: Context,
    private val mainRepository: MainRepository,
    private val firebaseDatabase: DatabaseReference
) : ViewModel() {

    val onUrlIsValid = SingleLiveEvent<String>()
    val onUrlIsInvalid = SingleLiveEvent<Error>()

    fun validateUrl(url: String) {
        if (url.startsWith("http")) {
            onUrlIsValid.invoke(url)
        } else {
            onUrlIsInvalid.invoke(java.lang.Error("URL invalid"))
        }
    }
}
