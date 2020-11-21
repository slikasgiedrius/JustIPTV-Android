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

fun M3UPlaylist.filterByPhrase(phrase: String?): ArrayList<M3UItem> {
    val noBlanks = this.skipBlanks()
    val noBlanksByPhrase = arrayListOf<M3UItem>()
    noBlanks.forEach {
        if (it.itemName!!.contains(phrase!!)) {
            noBlanksByPhrase.add(it)
        }
    }

    return noBlanksByPhrase
}