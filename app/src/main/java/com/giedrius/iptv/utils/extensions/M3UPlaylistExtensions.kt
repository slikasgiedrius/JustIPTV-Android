package com.giedrius.iptv.utils.extensions

import com.giedrius.iptv.data.model.parser.M3UItem
import com.giedrius.iptv.data.model.parser.M3UPlaylist

fun M3UPlaylist.skipBlanks(): ArrayList<M3UItem> {
    val playlistNoBlanks: ArrayList<M3UItem> = arrayListOf()
    this.playlistItems?.forEach {
        if (!it.itemName.isNullOrEmpty()) {
            playlistNoBlanks.add(it)
        }
    }
    return playlistNoBlanks
}