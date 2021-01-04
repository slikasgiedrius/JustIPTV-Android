package com.giedrius.iptv.ui.player

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.navArgs
import com.giedrius.iptv.R
import com.giedrius.iptv.databinding.ActivityPlayerBinding
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PlayerActivity : AppCompatActivity(R.layout.activity_player) {

    private lateinit var binding: ActivityPlayerBinding

    private val viewModel: PlayerViewModel by viewModels()
    private var mPlayer: SimpleExoPlayer? = null
    private var playWhenReady = true
    private var currentWindow = 0

    private val args: PlayerActivityArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPlayerBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        initPlayer()
    }

    private fun initPlayer() {
        mPlayer = SimpleExoPlayer.Builder(this).build()
        // Bind the player to the view.
        binding.videoView.player = mPlayer
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
            binding.videoView.resizeMode = AspectRatioFrameLayout.RESIZE_MODE_FILL
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            binding.videoView.resizeMode = AspectRatioFrameLayout.RESIZE_MODE_FIT
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