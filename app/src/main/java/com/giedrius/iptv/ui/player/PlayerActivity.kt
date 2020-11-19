package com.giedrius.iptv.ui.player

import android.content.res.Configuration
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.giedrius.iptv.R
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.player_fragment.*


@AndroidEntryPoint
class PlayerActivity : AppCompatActivity(R.layout.player_fragment) {

    private val viewModel: PlayerViewModel by viewModels()
    private var mPlayer: SimpleExoPlayer? = null
    private var playWhenReady = true
    private var currentWindow = 0

    private fun initPlayer() {
        mPlayer = SimpleExoPlayer.Builder(this).build()
        // Bind the player to the view.
        video_view.player = mPlayer
        mPlayer!!.playWhenReady = true
        val mediaItem: MediaItem =
            MediaItem.fromUri("http://uran.iptvboss.net:80/GiedriusSlikas/GiedriusSlikas/46925")
        mPlayer!!.setMediaItem(mediaItem)
        mPlayer!!.prepare()
    }

    override fun onStart() {
        super.onStart()
        initPlayer()
    }

    override fun onResume() {
        super.onResume()
        hideSystemUI()
        if (mPlayer == null) {
            initPlayer()
        }
    }

    override fun onPause() {
        super.onPause()
        releasePlayer()
    }

    override fun onStop() {
        super.onStop()
        releasePlayer()
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            video_view.resizeMode = AspectRatioFrameLayout.RESIZE_MODE_FILL
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            video_view.resizeMode = AspectRatioFrameLayout.RESIZE_MODE_FIT
        }
    }

    private fun releasePlayer() {
        if (mPlayer == null) {
            return
        }
        playWhenReady = mPlayer!!.playWhenReady
        currentWindow = mPlayer!!.currentWindowIndex
        mPlayer!!.release()
        mPlayer = null
    }


    private fun hideSystemUI() {
        // Enables regular immersive mode.
        // For "lean back" mode, remove SYSTEM_UI_FLAG_IMMERSIVE.
        // Or for "sticky immersive," replace it with SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_IMMERSIVE
                // Set the content to appear under the system bars so that the
                // content doesn't resize when the system bars hide and show.
                or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                // Hide the nav bar and status bar
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_FULLSCREEN)
    }

    private fun showSystemUI() {
        window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)
    }
}