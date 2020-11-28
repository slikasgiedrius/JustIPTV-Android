package com.giedrius.iptv.data.model

data class Playlist(
    var playlistName: String? = null,
    var playlistParams: String? = null,
    var playlistItems: ArrayList<Channel>?
)