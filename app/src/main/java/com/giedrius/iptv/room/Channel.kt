package com.giedrius.iptv.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "channel_table")
data class Channel (
    var itemId: String?,
    var itemName: String?,
    var itemLogo: String?,
    var itemGroup: String?,
    var itemUrl: String?,
    var itemDuration: String?
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}