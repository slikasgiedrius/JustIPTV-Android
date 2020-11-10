package com.giedrius.iptv.data.api

import com.giedrius.iptv.data.model.Data
import retrofit2.Response
import javax.inject.Inject

class ApiHelperImpl @Inject constructor(
    private val apiService: ApiService
) : ApiHelper {

    override suspend fun getData(): Response<Data> = apiService.getData()
}