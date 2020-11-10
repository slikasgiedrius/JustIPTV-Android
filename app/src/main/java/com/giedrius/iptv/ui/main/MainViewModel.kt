package com.giedrius.iptv.ui.main

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.giedrius.iptv.data.model.Data
import com.giedrius.iptv.data.repository.MainRepository
import com.giedrius.iptv.utils.toast
import kotlinx.coroutines.launch

class MainViewModel @ViewModelInject constructor(
    private val mainRepository: MainRepository
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
                } else {
                    error
                }
            }
        }
    }
}