package com.giedrius.iptv.ui.channels

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.giedrius.iptv.R
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.channels_fragment.*
import kotlinx.android.synthetic.main.input_fragment.button
import kotlinx.android.synthetic.main.input_fragment.textView

@AndroidEntryPoint
class ChannelsFragment : Fragment(R.layout.channels_fragment) {

    private val viewModel: ChannelsViewModel by viewModels()
    private val args: ChannelsFragmentArgs by navArgs()
    private lateinit var linearLayoutManager: LinearLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)
        context?.let { handleObservers(it) }

        retrieveDataFromArgs()
        setupListeners()
    }

    private fun retrieveDataFromArgs() {
        textView.text = args.url
    }

    private fun setupListeners() {
        button.setOnClickListener {
            findNavController().navigate(R.id.action_channelsFragment_to_playerActivity)
        }
    }

    private fun handleObservers(context: Context) {
        viewModel.onFetchedChannels.observe(viewLifecycleOwner) {
            linearLayoutManager = LinearLayoutManager(context)
            recyclerView.layoutManager = linearLayoutManager
            recyclerView.adapter = it.playlistItems?.let { it1 -> ChannelsAdapter(it1, context) }
        }
    }
}
