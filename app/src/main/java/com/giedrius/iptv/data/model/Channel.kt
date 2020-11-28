package com.giedrius.iptv.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "channel_table")
data class Channel(
    @PrimaryKey var id: Int,
    var itemId: String?,
    var itemName: String?,
    var itemLogo: String?,
    var itemGroup: String?,
    var itemUrl: String?,
    var itemDuration: String?
)