package com.giedrius.iptv.database.favourites

import androidx.lifecycle.LiveData
import androidx.room.*
import com.giedrius.iptv.data.entity.FavouriteEntity

@Dao
interface FavouritesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addFavourite(favourite: FavouriteEntity)

    @Update
    suspend fun updateFavourite(favourite: FavouriteEntity)

    @Delete
    suspend fun deleteFavourite(favourite: FavouriteEntity)

    @Query("DELETE FROM favourites_table")
    suspend fun deleteAllFavourites()

    @Query("SELECT * FROM favourites_table ORDER BY id ASC")
    fun getSavedFavourites(): LiveData<List<FavouriteEntity>>
}