package com.giedrius.iptv.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favourites_table")
data class FavouriteEntity(
    @PrimaryKey var id: Int,
    var itemId: String?,
    var itemName: String?,
    var itemLogo: String?,
    var itemGroup: String?,
    var itemUrl: String?,
    var itemDuration: String?
)