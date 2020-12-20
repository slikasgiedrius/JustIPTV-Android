package com.giedrius.iptv.data.repository

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.giedrius.iptv.utils.Preferences
import com.giedrius.iptv.utils.SingleLiveEvent
import com.lyrebirdstudio.fileboxlib.core.*
import dagger.hilt.android.qualifiers.ApplicationContext
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import kotlin.math.ceil

class DownloadRepository @Inject constructor(
    @ApplicationContext private val application: Context,
    private val preferences: Preferences
) {
    val onDownloadProgressChanged = MutableLiveData<Int>()
    val onFilePatchChanged = MutableLiveData<String>()
    val onDataDownloaded = MutableLiveData<Boolean>()

    fun downloadFile(initialUrl: String?) {
        val fileBoxRequest = initialUrl?.let { FileBoxRequest(it) }

        val fileBoxConfig = FileBoxConfig.FileBoxConfigBuilder()
            .setCryptoType(CryptoType.NONE)
            .setTTLInMillis(TimeUnit.DAYS.toMillis(7))
            .setDirectory(DirectoryType.CACHE)
            .build()

        fileBoxRequest?.let {
            FileBoxProvider.newInstance(application, fileBoxConfig)
                .get(it)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ fileBoxResponse ->
                    when (fileBoxResponse) {
                        is FileBoxResponse.Started -> Timber.d("File download started")
                        is FileBoxResponse.Downloading -> displayProgress(fileBoxResponse.progress)
                        is FileBoxResponse.Complete -> fileBoxResponse.record.decryptedFilePath?.let { path ->
                            Timber.d("Download complete")
                            onFilePatchChanged.postValue(path)
                            onDataDownloaded.postValue(true)
                            preferences.setFilePath(path)
                        }
                        is FileBoxResponse.Error -> Timber.e("Error while downloading file ${fileBoxResponse.throwable}")
                    }
                }, Timber::e)
        }
    }

    private fun displayProgress(progress: Float) {
        val percent = ceil((progress) * 100).toInt()
        onDownloadProgressChanged.postValue(percent)
    }
}