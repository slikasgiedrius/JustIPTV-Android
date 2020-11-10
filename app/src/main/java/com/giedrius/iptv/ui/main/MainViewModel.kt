package com.giedrius.iptv.ui.main

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.giedrius.iptv.data.model.User
import com.giedrius.iptv.data.repository.MainRepository
import kotlinx.coroutines.launch

class MainViewModel @ViewModelInject constructor(
    private val mainRepository: MainRepository
) : ViewModel() {

    val users = MutableLiveData<User>()

    init {
        fetchUsers()
    }

    private fun fetchUsers() {
        viewModelScope.launch {
            mainRepository.getUser().let {
                if (it.isSuccessful) {
                    users.postValue(it.body())
                } else {
                    println("Error ${it.body()}")
                }
            }
        }
    }
}