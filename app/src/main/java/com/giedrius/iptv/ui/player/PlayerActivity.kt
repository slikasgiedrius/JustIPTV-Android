package com.giedrius.iptv.ui.player

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.giedrius.iptv.R
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.SimpleExoPlayer
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.player_fragment.*


@AndroidEntryPoint
class PlayerActivity : AppCompatActivity(R.layout.player_fragment) {

    private val viewModel: PlayerViewModel by viewModels()
    private var player: SimpleExoPlayer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initializePlayer()
    }

    private fun initializePlayer() {
        player = SimpleExoPlayer.Builder(this).build()
        video_view.player = player

        val mediaItem: MediaItem =
            MediaItem.fromUri("http://uran.iptvboss.net:80/GiedriusSlikas/GiedriusSlikas/46925")
        player!!.setMediaItem(mediaItem)

        player!!.setPlayWhenReady(true);
        player!!.prepare();
    }
}