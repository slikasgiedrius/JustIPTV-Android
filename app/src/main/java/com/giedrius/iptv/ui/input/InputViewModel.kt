package com.giedrius.iptv.ui.input

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.giedrius.iptv.data.model.Data
import com.giedrius.iptv.data.repository.MainRepository
import com.giedrius.iptv.utils.SingleLiveEvent
import com.google.firebase.database.DatabaseReference
import kotlinx.coroutines.launch

class InputViewModel @ViewModelInject constructor(
        private val mainRepository: MainRepository,
        private val firebaseDatabase: DatabaseReference
) : ViewModel() {

    val onUrlIsValid = SingleLiveEvent<String>()
    val onUrlIsInvalid = SingleLiveEvent<Error>()

    fun validateUrl(url: String) {
        if (url.startsWith("https")) {
            onUrlIsValid.invoke(url)
        } else {
            onUrlIsInvalid.invoke(java.lang.Error("URL invalid"))
        }
    }
}