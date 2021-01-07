package com.giedrius.iptv.ui

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.giedrius.iptv.data.repository.DownloadRepository
import com.giedrius.iptv.utils.Preferences
import com.giedrius.iptv.utils.SingleLiveEvent

class MainActivityViewModel @ViewModelInject constructor(
    val preferences: Preferences
) : ViewModel()
