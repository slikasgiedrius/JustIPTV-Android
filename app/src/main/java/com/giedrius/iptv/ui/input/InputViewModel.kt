package com.giedrius.iptv.ui.input

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.giedrius.iptv.data.model.Data
import com.giedrius.iptv.data.repository.MainRepository
import com.google.firebase.database.DatabaseReference
import kotlinx.coroutines.launch

class InputViewModel @ViewModelInject constructor(
    private val mainRepository: MainRepository,
    private val firebaseDatabase: DatabaseReference
) : ViewModel() {

    val onUrlIsValid = MutableLiveData<Boolean>()
    val onUrlIsInvalid = MutableLiveData<Boolean>()

    fun validateUrl(url: String) {
        onUrlIsValid.value = true
    }
}