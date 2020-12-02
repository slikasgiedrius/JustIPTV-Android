package com.giedrius.iptv.ui.channels

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.giedrius.iptv.R
import com.giedrius.iptv.data.model.Channel
import com.giedrius.iptv.utils.listeners.RecyclerViewClickListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.channels_fragment.*
import timber.log.Timber

@AndroidEntryPoint
class ChannelsFragment : Fragment(R.layout.channels_fragment), RecyclerViewClickListener {

    private lateinit var adapter: ChannelsAdapter

    private val viewModel: ChannelsViewModel by viewModels()
    private var items: List<Channel> = arrayListOf()

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)

        setupListeners()
        setupRecyclerView()
        handleObservers()
    }

    override fun onPlaylistClickListener(item: Channel) {
        val action = ChannelsFragmentDirections.actionChannelsFragmentToPlayerActivity(
            item.itemUrl.toString()
        )
        view?.findNavController()?.navigate(action)
    }

    private fun handleObservers() {
        viewModel.channelsRepository.savedChannels.observe(viewLifecycleOwner) {
            items = it
            adapter.update(it)
            viewModel.detectIfDownloadNeeded(items.count())
        }
        viewModel.onFetchedChannels.observe(viewLifecycleOwner) {
            adapter.update(it)
        }
        viewModel.onProgressChanged.observe(viewLifecycleOwner) {
            progressBar.progress = it
        }
    }

    private fun setupListeners() {
        etSearch.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {}
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                viewModel.loadChannelsNoUpdate(items, etSearch.text.toString())
            }
        })
    }

    private fun setupRecyclerView() {
        val linearLayoutManager = LinearLayoutManager(context)
        recyclerView.layoutManager = linearLayoutManager
        adapter = ChannelsAdapter(items, requireContext(), this)
        recyclerView.adapter = adapter
    }
}
