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

    fun validateUrl(url: String) {
        if (url.startsWith("http")) {
            onUrlIsValid.invoke(url)
        } else {
            onUrlIsInvalid.invoke(java.lang.Error("URL invalid"))
        }
    }
}
