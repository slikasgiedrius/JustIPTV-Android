package com.giedrius.iptv.ui.settings

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.giedrius.iptv.data.repository.ChannelsRepository
import com.giedrius.iptv.data.repository.FavouritesRepository
import com.giedrius.iptv.utils.Preferences
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SettingsViewModel @ViewModelInject constructor(
    val preferences: Preferences,
    private val channelsRepository: ChannelsRepository,
    val favouritesRepository: FavouritesRepository
) : ViewModel() {

    fun clearSharedPreferences() = preferences.clearSharedPreference()

    fun deleteAllChannels() {
        viewModelScope.launch(Dispatchers.IO) {
            channelsRepository.deleteAllChannels()
        }
    }

    fun deleteAllFavouriteChannels() {
        viewModelScope.launch(Dispatchers.IO) {
            favouritesRepository.deleteAllFavourites()
        }
    }
}