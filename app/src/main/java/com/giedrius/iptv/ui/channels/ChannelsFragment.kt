package com.giedrius.iptv.ui.channels

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.giedrius.iptv.R
import com.giedrius.iptv.data.model.Channel
import com.giedrius.iptv.databinding.ChannelsFragmentBinding
import com.giedrius.iptv.utils.extensions.hideKeyboard
import com.giedrius.iptv.utils.listeners.ChannelClickListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChannelsFragment : Fragment(R.layout.channels_fragment), ChannelClickListener {

    private var _binding: ChannelsFragmentBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: ChannelsAdapter

    private lateinit var viewModel: ChannelsViewModel
    private var items: List<Channel> = arrayListOf()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = ChannelsFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(ChannelsViewModel::class.java)

        setupListeners()
        setupRecyclerView()
        handleObservers()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onChannelClickListener(item: Channel) {
        val action = ChannelsFragmentDirections.actionChannelsFragmentToPlayerActivity(
            item.itemUrl.toString()
        )
        view?.findNavController()?.navigate(action)
    }

    override fun onFavouriteClickListener(item: Channel) {
        viewModel.addFavourite(item)
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
        viewModel.downloadRepository.onDownloadProgressChanged.observe(viewLifecycleOwner) {
            binding.progressBar.progress = it
        }
        viewModel.onDataMissing.observe(viewLifecycleOwner) {
            startInputActivity(it)
        }
        viewModel.downloadRepository.onFilePatchChanged.observe(viewLifecycleOwner) {
            viewModel.loadChannels(it)
        }
    }

    private fun setupListeners() {
        binding.etSearch.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {}
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                viewModel.performChannelSearch(items, binding.etSearch.text.toString())
            }
        })

        binding.etSearch.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                hideKeyboard()
            }
            false
        }
    }

    private fun setupRecyclerView() {
        val linearLayoutManager = LinearLayoutManager(context)
        binding.recyclerView.layoutManager = linearLayoutManager
        adapter = ChannelsAdapter(items, requireContext(), this)
        binding.recyclerView.adapter = adapter
    }

    private fun startInputActivity(isDataMissing: Boolean) {
        if (isDataMissing) {
            val action = ChannelsFragmentDirections.actionChannelsFragmentToInputActivity()
            view?.findNavController()?.navigate(action)
        }
    }
}
