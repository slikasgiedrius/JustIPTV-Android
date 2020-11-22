package com.giedrius.iptv.ui.channels

import android.content.Context
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.giedrius.iptv.data.model.parser.M3UItem
import com.giedrius.iptv.data.model.parser.M3UParser
import com.giedrius.iptv.utils.Preferences
import com.giedrius.iptv.utils.SingleLiveEvent
import com.giedrius.iptv.utils.extensions.filterByPhrase
import com.lyrebirdstudio.fileboxlib.core.*
import dagger.hilt.android.qualifiers.ApplicationContext
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch
import timber.log.Timber
import java.io.File
import java.io.FileInputStream
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class ChannelsViewModel @ViewModelInject constructor(
    @ApplicationContext private val application: Context,
    preferences: Preferences
) : ViewModel() {

    var channelsDownloader: ChannelsDownloader = ChannelsDownloader(application, preferences, this)

    val onFetchedChannels = SingleLiveEvent<ArrayList<M3UItem>>()
}