package com.giedrius.iptv.ui.introduction

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.giedrius.iptv.data.model.Channel
import com.giedrius.iptv.data.repository.ChannelsRepository
import com.giedrius.iptv.data.repository.DownloadRepository
import com.giedrius.iptv.utils.PlaylistParser
import com.giedrius.iptv.utils.Preferences
import com.giedrius.iptv.utils.SingleLiveEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileInputStream

class IntroductionViewModel @ViewModelInject constructor(
    val downloadRepository: DownloadRepository,
    val channelsRepository: ChannelsRepository,
    val preferences: Preferences
) : ViewModel() {

    val onUrlIsValid = MutableLiveData<Boolean>()
    val onExtractionCompleted = MutableLiveData<Boolean>()
    val onSavingCompleted = MutableLiveData<Boolean>()

    fun validateUrl(url: String) {
        url.apply {
            saveUrlIfNeeded(this)
            onUrlIsValid.postValue(true)
        }
    }

    private fun saveUrlIfNeeded(url: String) {
        if (preferences.getInitialUrl() == null){
            preferences.setInitialUrl(url)
        }
    }

    fun loadChannels(name: String?) {
        if (name.isNullOrEmpty()) return
        viewModelScope.launch(Dispatchers.IO) {
            val parser = PlaylistParser()
            val inputStream = FileInputStream(File(name))
            val playlist = parser.parseFile(inputStream)
            playlist.playlistItems?.let {
                onExtractionCompleted.postValue(true)
                saveChannels(it)
            }
        }
    }

    private fun saveChannels(channels: List<Channel>) {
        viewModelScope.launch(Dispatchers.IO) {
            channelsRepository.uploadChannels(channels)
            withContext (Dispatchers.Main) {
                onSavingCompleted.postValue(true)
            }
        }
    }

    fun downloadContent() {
        viewModelScope.launch(Dispatchers.IO) {
            downloadRepository.downloadFile(preferences.getInitialUrl())
        }
    }
}