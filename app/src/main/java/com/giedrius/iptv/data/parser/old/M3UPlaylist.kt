package com.giedrius.iptv.data.parser.old

@Deprecated("Use NewM3UPlaylist")
class M3UPlaylist {

    var playlistName: String? = null

    var playlistParams: String? = null

    var playlistItems: ArrayList<M3UItem>? = null
}