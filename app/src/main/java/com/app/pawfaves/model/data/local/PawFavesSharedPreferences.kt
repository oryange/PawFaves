package com.app.pawfaves.model.data.local

internal interface PawFavesSharedPreferences {
    fun addFavorite(newItem: String): Boolean

    fun removeFavorite(itemToRemove: String): Boolean

    fun getFavorite(): List<String>
}
