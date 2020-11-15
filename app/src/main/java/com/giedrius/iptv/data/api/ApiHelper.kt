package com.giedrius.iptv.data.api

import com.giedrius.iptv.data.model.Data
import retrofit2.Response

interface ApiHelper {
  suspend fun getData(): Response<Data>
}