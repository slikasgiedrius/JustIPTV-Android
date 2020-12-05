package com.giedrius.iptv.utils

import com.giedrius.iptv.data.entity.ChannelEntity
import com.giedrius.iptv.data.entity.FavouriteEntity
import com.giedrius.iptv.data.model.Channel
import com.giedrius.iptv.data.model.Favourite

fun ChannelEntity.toChannel(): Channel {
    return Channel(id, itemId, itemName, itemLogo, itemGroup, itemUrl, itemDuration)
}

fun Channel.toChannelEntity(): ChannelEntity {
    return ChannelEntity(
        id,
        itemId,
        itemName,
        itemLogo,
        itemGroup,
        itemUrl,
        itemDuration
    )
}

fun FavouriteEntity.toFavourite(): Favourite {
    return Favourite(id, Channel(id, itemId, itemName, itemLogo, itemGroup, itemUrl, itemDuration))
}

fun Favourite.toFavouriteEntity(): FavouriteEntity {
    return FavouriteEntity(
        id,
        channel.itemId,
        channel.itemName,
        channel.itemLogo,
        channel.itemGroup,
        channel.itemUrl,
        channel.itemDuration
    )
}