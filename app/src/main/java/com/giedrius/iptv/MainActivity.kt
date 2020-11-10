package com.giedrius.iptv

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.giedrius.iptv.ui.main.MainFragment
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.HiltAndroidApp

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.main_activity)
    if (savedInstanceState == null) {
      supportFragmentManager.beginTransaction()
          .replace(R.id.container, MainFragment.newInstance())
          .commitNow()
    }
  }
}