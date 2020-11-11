package com.giedrius.iptv.ui.main

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.giedrius.iptv.data.model.Data
import com.giedrius.iptv.data.repository.MainRepository
import com.giedrius.iptv.db.FirebaseDB
import kotlinx.coroutines.launch

class MainViewModel @ViewModelInject constructor(
    private val mainRepository: MainRepository,
    private val firebaseDB: FirebaseDB
) : ViewModel() {

    val users = MutableLiveData<Data>()
    val error = MutableLiveData<Boolean>()

    init {
        fetchData()
    }

    private fun fetchData() {
        viewModelScope.launch {
            mainRepository.getData().let {
                if (it.isSuccessful) {
                    users.postValue(it.body())
                    //use firebaseDB.logValidUrl to store data in db
                } else {
                    error
                }
            }
        }
    }
}