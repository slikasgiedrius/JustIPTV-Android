package com.giedrius.iptv.ui.input

import android.content.Context
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.giedrius.iptv.data.repository.MainRepository
import com.giedrius.iptv.parser.M3UParser
import com.giedrius.iptv.utils.SingleLiveEvent
import com.google.firebase.database.DatabaseReference
import com.lyrebirdstudio.fileboxlib.core.*
import dagger.hilt.android.qualifiers.ApplicationContext
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch
import timber.log.Timber
import java.io.File
import java.io.FileInputStream
import java.util.concurrent.TimeUnit

class InputViewModel @ViewModelInject constructor(
    @ApplicationContext private val application: Context,
    private val mainRepository: MainRepository,
    private val firebaseDatabase: DatabaseReference
) : ViewModel() {

    val onUrlIsValid = SingleLiveEvent<String>()
    val onUrlIsInvalid = SingleLiveEvent<Error>()

    init {
        downloadIptvFile()
    }

    fun validateUrl(url: String) {
        if (url.startsWith("https")) {
            onUrlIsValid.invoke(url)
        } else {
            onUrlIsInvalid.invoke(java.lang.Error("URL invalid"))
        }
    }

    private fun downloadIptvFile() {
        val fileBoxRequest = FileBoxRequest("http://uran.iptvboss.net:80/get.php?username=GiedriusSlikas&password=GiedriusSlikas&type=m3u_plus&output=ts")

        val fileBoxConfig = FileBoxConfig.FileBoxConfigBuilder()
            .setCryptoType(CryptoType.NONE)
            .setTTLInMillis(TimeUnit.DAYS.toMillis(7))
            .setDirectory(DirectoryType.CACHE)
            .build()

        viewModelScope.launch {
            FileBoxProvider.newInstance(application, fileBoxConfig)
                .get(fileBoxRequest)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe ({ fileBoxResponse ->
                    when (fileBoxResponse) {
                        is FileBoxResponse.Downloading -> {
                            val progress: Float = fileBoxResponse.progress
                            val ongoingRecord: Record = fileBoxResponse.record
                        }
                        is FileBoxResponse.Complete -> {
                            val savedRecord: Record = fileBoxResponse.record
                            val savedPath = fileBoxResponse.record.getReadableFilePath()

                            loadChannels(savedRecord.decryptedFilePath!!)
                        }
                        is FileBoxResponse.Error -> {
                            val savedRecord: Record = fileBoxResponse.record
                            val error = fileBoxResponse.throwable
                        }
                    }
                }, Timber::e)
        }
    }

    private fun loadChannels(name: String) {
        val parser = M3UParser()
        val inputStream = FileInputStream(File(name))
        val playlist = parser.parseFile(inputStream)
        Timber.d(playlist.playlistItems?.count().toString())
    }
}
