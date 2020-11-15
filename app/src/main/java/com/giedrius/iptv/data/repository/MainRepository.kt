package com.giedrius.iptv.data.repository

import com.giedrius.iptv.data.api.ApiHelper
import javax.inject.Inject

class MainRepository
@Inject constructor(
    private val apiHelper: ApiHelper
) {

    suspend fun getData() = apiHelper.getData()
}
