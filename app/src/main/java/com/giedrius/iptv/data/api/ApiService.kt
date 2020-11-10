package com.giedrius.iptv.data.api

import com.giedrius.iptv.data.model.User
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {
    @GET("json/1")
    suspend fun getUser(): Response<User>
}