package com.giedrius.iptv

import com.giedrius.iptv.data.model.Data
import com.giedrius.iptv.data.repository.MainRepository
import com.giedrius.iptv.ui.main.MainViewModel
import com.google.firebase.database.DatabaseReference
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import okhttp3.Dispatcher
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import retrofit2.Response

@HiltAndroidTest
class MainTest {
    var mainRepository: MainRepository = mockk(relaxed = true)
    var firebaseDatabase: DatabaseReference = mockk(relaxed = true)
    lateinit var viewModel: MainViewModel

    @Before
    fun setUp() {
        viewModel = MainViewModel(mainRepository, firebaseDatabase)
    }

    @Test
    fun mainFragment() {
        //mock coroutine
//        runBlocking {
//            mainRepository.getData() returns Response.success(Data(id = 1, value = "Test"))
//        }

    }
}