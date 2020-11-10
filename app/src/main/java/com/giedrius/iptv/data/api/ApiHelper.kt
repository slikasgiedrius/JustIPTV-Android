package com.giedrius.iptv.data.api

import com.giedrius.iptv.data.model.User
import retrofit2.Response

interface ApiHelper {
    suspend fun getUser(): Response<User>
}