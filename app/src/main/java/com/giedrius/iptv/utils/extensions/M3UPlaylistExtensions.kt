package com.giedrius.iptv.utils.extensions

import com.giedrius.iptv.data.model.parser.NewM3UItem
import com.giedrius.iptv.data.model.parser.NewM3UPlaylist

fun NewM3UPlaylist.skipBlanks(): ArrayList<NewM3UItem> {
    val playlistNoBlanks: ArrayList<NewM3UItem> = arrayListOf()
    this.playlistItems?.forEach {
        if (!it.itemName.isNullOrEmpty()) {
            playlistNoBlanks.add(it)
        }
    }
    return playlistNoBlanks
}

fun NewM3UPlaylist.filterByPhrase(phrase: String?): ArrayList<NewM3UItem> {
    val noBlanks = this.skipBlanks()
    val noBlanksByPhrase = arrayListOf<NewM3UItem>()
    noBlanks.forEach {
        if (it.itemName!!.contains(phrase!!)) {
            noBlanksByPhrase.add(it)
        }
    }

    return noBlanksByPhrase
}