package com.giedrius.iptv.ui.player

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.giedrius.iptv.R
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.SimpleExoPlayer
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.input_fragment.button
import kotlinx.android.synthetic.main.player_fragment.*


@AndroidEntryPoint
class PlayerFragment : Fragment(R.layout.player_fragment) {

    private val viewModel: PlayerViewModel by viewModels()
    private var player: SimpleExoPlayer? = null

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)

        setupListeners()
        show()
    }

    private fun setupListeners() {
        button.setOnClickListener {
            findNavController().navigate(R.id.action_playerFragment_to_mainFragment)
        }
    }

    private fun show() {
        player = context?.let { SimpleExoPlayer.Builder(it).build() }
        video_view.setPlayer(player)

        val mediaItem: MediaItem = MediaItem.fromUri("http://uran.iptvboss.net:80/GiedriusSlikas/GiedriusSlikas/46925")
        player!!.setMediaItem(mediaItem)

        player!!.setPlayWhenReady(true);
        player!!.prepare();

    }

}
