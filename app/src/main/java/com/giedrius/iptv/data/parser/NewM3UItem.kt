package com.giedrius.iptv.data.parser

import androidx.room.PrimaryKey

class NewM3UItem {
    @PrimaryKey(autoGenerate = true)
    var foodId: Int = 0
    var itemId: String? = null
    var itemName: String? = null
    var itemLogo: String? = null
    var itemGroup: String? = null
    var itemUrl: String? = null
    var itemDuration: String? = null
}