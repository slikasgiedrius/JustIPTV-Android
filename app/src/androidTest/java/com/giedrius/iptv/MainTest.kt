package com.giedrius.iptv

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.fragment.app.viewModels
import com.giedrius.iptv.ui.main.MainFragment
import com.giedrius.iptv.ui.main.MainViewModel
import com.giedrius.iptv.utils.launchFragmentInHiltContainer
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class MainTest {
    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Before
    fun init() {
        hiltRule.inject()
    }

    @Test
    fun mainFragment() {
        val scenario = launchFragmentInHiltContainer<MainFragment> {}
        
    }
}