package com.giedrius.iptv.utils.extensions

import com.giedrius.iptv.data.model.Channel
import com.giedrius.iptv.data.parser.NewM3UPlaylist

fun NewM3UPlaylist.skipBlanks(): ArrayList<Channel> {
    val playlistNoBlanks: ArrayList<Channel> = arrayListOf()
    this.playlistItems?.forEach {
        if (!it.itemName.isNullOrEmpty()) {
            playlistNoBlanks.add(it)
        }
    }
    return playlistNoBlanks
}

fun NewM3UPlaylist.filterByPhrase(phrase: String?): ArrayList<Channel> {
    val noBlanks = this.skipBlanks()
    val noBlanksByPhrase = arrayListOf<Channel>()
    noBlanks.forEach {
        if (it.itemName!!.contains(phrase!!)) {
            noBlanksByPhrase.add(it)
        }
    }

    return noBlanksByPhrase
}