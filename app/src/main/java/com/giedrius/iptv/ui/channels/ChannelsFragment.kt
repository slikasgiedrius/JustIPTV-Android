package com.giedrius.iptv.ui.channels

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.giedrius.iptv.R
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.input_fragment.*

@AndroidEntryPoint
class ChannelsFragment : Fragment(R.layout.channels_fragment) {

    private val viewModel: ChannelsViewModel by viewModels()
    private val args: ChannelsFragmentArgs by navArgs()

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)
        textView.text = args.url
        button.setOnClickListener {
            findNavController().navigate(R.id.action_channelsFragment_to_playerFragment)
        }
    }
}
