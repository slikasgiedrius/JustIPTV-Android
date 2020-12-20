package com.giedrius.iptv.ui.introduction

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.giedrius.iptv.data.repository.DownloadRepository
import com.giedrius.iptv.utils.Preferences

class IntroductionViewModel @ViewModelInject constructor(
    val downloadRepository: DownloadRepository,
    val preferences: Preferences
): ViewModel() {

    init {
        downloadRepository.downloadFile(preferences.getInitialUrl())
    }
}