package com.giedrius.iptv.data.model

import com.squareup.moshi.Json

data class Data(
        @Json(name = "id")
        val id: Int = 0,
        @Json(name = "value")
        val value: String = ""
)