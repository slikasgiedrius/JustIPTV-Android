package com.giedrius.iptv.ui.channels

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.giedrius.iptv.R
import com.giedrius.iptv.data.parser.NewM3UItem
import com.giedrius.iptv.utils.listeners.RecyclerViewClickListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.channels_fragment.*
import timber.log.Timber

@AndroidEntryPoint
class ChannelsFragment : Fragment(R.layout.channels_fragment), RecyclerViewClickListener {

    private lateinit var adapter: ChannelsAdapter

    private val viewModel: ChannelsViewModel by viewModels()
    private var items: ArrayList<NewM3UItem> = arrayListOf()

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)

        handleObservers()
        setupListeners()
        setupRecyclerView()
        viewModel.channelsDownloader.checkForSavedPlaylist()
    }

    override fun onPlaylistClickListener(item: NewM3UItem) {
        Timber.d("clicked item $item")
        viewModel.saveChannelToDatabase(item)
//        viewModel.deleteAllUsers()
        val action = ChannelsFragmentDirections.actionChannelsFragmentToPlayerActivity(
            item.itemUrl.toString()
        )
        view?.findNavController()?.navigate(action)
    }

    private fun handleObservers() {
        viewModel.onFetchedChannels.observe(viewLifecycleOwner) {
            adapter.update(it)
        }
        viewModel.channelRepository.readAllData.observe(viewLifecycleOwner) {
            Timber.d("All data $it")
        }

    }

    private fun setupListeners() {
        btnSearch.setOnClickListener {
            viewModel.channelsDownloader.loadChannelsNoUpdate(etSearch.text.toString())
        }
    }

    private fun setupRecyclerView() {
        val linearLayoutManager = LinearLayoutManager(context)
        recyclerView.layoutManager = linearLayoutManager
        adapter = ChannelsAdapter(items, requireContext(), this)
        recyclerView.adapter = adapter
    }
}
