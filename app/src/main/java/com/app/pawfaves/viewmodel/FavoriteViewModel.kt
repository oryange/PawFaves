package com.app.pawfaves.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.app.pawfaves.model.data.entities.MessageData
import com.app.pawfaves.model.data.local.PawFavesSharedPreferences

internal class FavoriteViewModel(private val pawFavesSharedPreferences: PawFavesSharedPreferences) :
    ViewModel() {
    private val _favoriteBreedsList = MutableLiveData<List<MessageData>>()
    val favoriteBreedsList = _favoriteBreedsList


    fun setFavoriteItem(newItem: String) {
        if (getFavorite().contains(newItem)) removeFavorite(newItem)
        else {
            addFavorite(newItem)
        }
        verifyIsFavorite()
    }

    fun verifyIsFavorite() {
        val listFavorites = getFavorite()
        listFavorites.let { list ->
            if (list.isNotEmpty()) {
                _favoriteBreedsList.value = list.map { MessageData(it, listFavorites.contains(it)) }
            } else {
                _favoriteBreedsList.value = emptyList()
            }
        }
    }

    private fun addFavorite(newItem: String): Boolean =
        pawFavesSharedPreferences.addFavorite(newItem)

    private fun removeFavorite(itemToRemove: String): Boolean =
        pawFavesSharedPreferences.removeFavorite(itemToRemove)

    private fun getFavorite(): List<String> = pawFavesSharedPreferences.getFavorite()
}
