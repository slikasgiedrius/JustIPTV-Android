package com.giedrius.iptv.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.giedrius.iptv.data.model.Favourite
import com.giedrius.iptv.database.favourites.FavouritesDao
import com.giedrius.iptv.utils.toFavourite
import com.giedrius.iptv.utils.toFavouriteEntity
import javax.inject.Inject

class FavouritesRepository @Inject constructor(private val favouritesDao: FavouritesDao) {

    val savedFavourites: LiveData<List<Favourite>> = Transformations.map(favouritesDao.getSavedFavourites()) { it.map { entity -> entity.toFavourite() } }

    suspend fun addFavourite(favourite: Favourite) = favouritesDao.addFavourite(favourite.toFavouriteEntity())

    suspend fun updateFavourite(favourite: Favourite) = favouritesDao.updateFavourite(favourite.toFavouriteEntity())

    suspend fun deleteFavourite(favourite: Favourite) = favouritesDao.deleteFavourite(favourite.toFavouriteEntity())

    suspend fun deleteAllFavourites() = favouritesDao.deleteAllFavourites()
}