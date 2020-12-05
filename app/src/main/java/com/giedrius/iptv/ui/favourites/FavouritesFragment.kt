package com.giedrius.iptv.ui.favourites

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.giedrius.iptv.R
import com.giedrius.iptv.data.model.Favourite
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavouritesFragment : Fragment(R.layout.favourites_fragment) {

    private val viewModel: FavouritesViewModel by viewModels()
    private var items: List<Favourite> = arrayListOf()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        handleObservers()
    }

    private fun handleObservers() {
        viewModel.favouritesRepository.savedFavourites.observe(viewLifecycleOwner) {
            items = it
        }
    }

}