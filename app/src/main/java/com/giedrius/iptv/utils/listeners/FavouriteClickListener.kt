package com.giedrius.iptv.utils.listeners

import com.giedrius.iptv.data.model.Channel
import com.giedrius.iptv.data.model.Favourite

interface FavouriteClickListener {
    fun onFavouriteClickListener(item: Favourite)
}