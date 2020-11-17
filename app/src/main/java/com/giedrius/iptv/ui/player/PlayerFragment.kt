package com.giedrius.iptv.ui.player

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.giedrius.iptv.R
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.input_fragment.*

@AndroidEntryPoint
class PlayerFragment : Fragment(R.layout.player_fragment) {

    private val viewModel: PlayerViewModel by viewModels()

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)
        button.setOnClickListener {
            findNavController().navigate(R.id.action_playerFragment_to_mainFragment)
        }
    }
}
