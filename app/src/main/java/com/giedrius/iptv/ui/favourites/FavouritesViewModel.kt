package com.giedrius.iptv.ui.favourites

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.giedrius.iptv.data.repository.FavouritesRepository

class FavouritesViewModel @ViewModelInject constructor(
    val favouritesRepository: FavouritesRepository
) : ViewModel() {

}