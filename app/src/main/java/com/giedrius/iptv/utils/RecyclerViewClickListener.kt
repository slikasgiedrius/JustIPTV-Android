package com.giedrius.iptv.utils

import com.giedrius.iptv.data.model.Channel

interface RecyclerViewClickListener {
    fun onPlaylistClickListener(item: Channel)
}