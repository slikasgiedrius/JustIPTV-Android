package com.giedrius.iptv.ui.favourites

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.giedrius.iptv.R
import com.giedrius.iptv.data.model.Favourite
import com.giedrius.iptv.ui.channels.ChannelsAdapter
import com.giedrius.iptv.ui.channels.ChannelsFragmentDirections
import com.giedrius.iptv.utils.listeners.FavouriteClickListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.channels_fragment.*

@AndroidEntryPoint
class FavouritesFragment : Fragment(R.layout.favourites_fragment), FavouriteClickListener {

    private lateinit var adapter: FavouritesAdapter

    private val viewModel: FavouritesViewModel by viewModels()
    private var items: List<Favourite> = arrayListOf()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        handleObservers()
    }

    override fun onFavouriteClickListener(item: Favourite) {
        val action = FavouritesFragmentDirections.actionFavouritesFragmentToPlayerActivity(
            item.channel.itemUrl.toString()
        )
        view?.findNavController()?.navigate(action)
    }

    private fun handleObservers() {
        viewModel.favouritesRepository.savedFavourites.observe(viewLifecycleOwner) {
            items = it
            adapter.update(it)
        }
    }

    private fun setupRecyclerView() {
        val linearLayoutManager = LinearLayoutManager(context)
        recyclerView.layoutManager = linearLayoutManager
        adapter = FavouritesAdapter(items, requireContext(), this)
        recyclerView.adapter = adapter
    }
}