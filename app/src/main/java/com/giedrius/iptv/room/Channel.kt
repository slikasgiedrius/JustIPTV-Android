package com.giedrius.iptv.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "channel_table")
data class Channel (
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    var itemId: String?,
    var itemName: String?,
    var itemLogo: String?,
    var itemGroup: String?,
    var itemUrl: String?,
    var itemDuration: String?
)