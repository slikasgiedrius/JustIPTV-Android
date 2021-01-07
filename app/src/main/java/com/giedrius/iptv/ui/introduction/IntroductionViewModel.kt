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

    val onExtractionCompleted = MutableLiveData<Boolean>()
    val onSavingCompleted = MutableLiveData<Boolean>()

    init {
        downloadRepository.downloadFile(preferences.getInitialUrl())
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
}