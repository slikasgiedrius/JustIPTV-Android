package com.giedrius.iptv.utils

import com.giedrius.iptv.data.entity.ChannelEntity
import com.giedrius.iptv.data.model.Channel

fun ChannelEntity.toChannel(): Channel {
    return Channel(id, itemId, itemName, itemLogo, itemGroup, itemUrl, itemDuration)
}

fun Channel.toChannelEntity() : ChannelEntity {
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