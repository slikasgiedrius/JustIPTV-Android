package com.giedrius.iptv.ui.player

import android.content.res.Configuration
import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.navArgs
import com.giedrius.iptv.R
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.player_activity.*


@AndroidEntryPoint
class PlayerActivity : AppCompatActivity(R.layout.player_activity) {

    private val viewModel: PlayerViewModel by viewModels()
    private var mPlayer: SimpleExoPlayer? = null
    private var playWhenReady = true
    private var currentWindow = 0

    private val args: PlayerActivityArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        initPlayer()
    }

    private fun initPlayer() {
        mPlayer = SimpleExoPlayer.Builder(this).build()
        // Bind the player to the view.
        video_view.player = mPlayer
        mPlayer!!.playWhenReady = true
        val mediaItem: MediaItem =
            MediaItem.fromUri(args.url)
        mPlayer!!.setMediaItem(mediaItem)
        mPlayer!!.prepare()
    }

    override fun onResume() {
        super.onResume()
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
}