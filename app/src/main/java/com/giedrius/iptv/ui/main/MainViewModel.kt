package com.giedrius.iptv.ui.main

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.giedrius.iptv.data.model.Data
import com.giedrius.iptv.data.repository.MainRepository
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainViewModel @ViewModelInject constructor(
    private val mainRepository: MainRepository
) : ViewModel() {

    @Inject lateinit var databaseReference: DatabaseReference

    val users = MutableLiveData<Data>()
    val error = MutableLiveData<Boolean>()

    init {
        fetchData()
        initDatabase()
    }

    private fun fetchData() {
        viewModelScope.launch {
            mainRepository.getData().let {
                if (it.isSuccessful) {
                    users.postValue(it.body())
                    //use databaseReference.logValidUrl to store data in db
                } else {
                    error
                }
            }
        }
    }

    private fun initDatabase() {
        databaseReference = Firebase.database.reference
    }
}