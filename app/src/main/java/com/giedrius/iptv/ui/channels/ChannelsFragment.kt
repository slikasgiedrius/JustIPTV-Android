package com.giedrius.iptv.ui.channels

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.giedrius.iptv.R
import com.giedrius.iptv.data.model.parser.M3UItem
import com.giedrius.iptv.utils.listeners.RecyclerViewClickListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.channels_fragment.*

@AndroidEntryPoint
class ChannelsFragment : Fragment(R.layout.channels_fragment), RecyclerViewClickListener {

    private val viewModel: ChannelsViewModel by viewModels()
    private val args: ChannelsFragmentArgs by navArgs()
    private lateinit var linearLayoutManager: LinearLayoutManager

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)

        setupListeners()
        initRecyclerView()
        context?.let { handleObservers(it) }
        viewModel.downloadFile(args.url)
    }

    private fun handleObservers(context: Context) {
        viewModel.onFetchedChannels.observe(viewLifecycleOwner) {
            recyclerView.adapter = ChannelsAdapter(
                it,
                context,
                this
            )
        }
    }

    override fun onPlaylistClickListener(item: M3UItem) {
        val action = ChannelsFragmentDirections.actionChannelsFragmentToPlayerActivity(
            item.itemUrl.toString()
        )
        view?.findNavController()?.navigate(action)
    }

    private fun initRecyclerView() {
        linearLayoutManager = LinearLayoutManager(context)
        recyclerView.layoutManager = linearLayoutManager
    }

    private fun setupListeners() {
        btnSearch.setOnClickListener {
            viewModel.loadChannels(etSearch.text.toString())
        }
    }
}
