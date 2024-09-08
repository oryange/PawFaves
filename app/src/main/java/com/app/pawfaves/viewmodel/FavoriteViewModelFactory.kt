package com.app.pawfaves.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.app.pawfaves.model.data.local.PawFavesSharedPreferences

internal class FavoriteViewModelFactory( private val pawFavesSharedPreferences: PawFavesSharedPreferences) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FavoriteViewModel::class.java)) {
            return FavoriteViewModel(pawFavesSharedPreferences) as T
        }
        throw IllegalArgumentException("Unknown HomeViewModel class")
    }
}
