package com.app.pawfaves.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.app.pawfaves.model.data.local.PawFavesSharedPreferences
import com.app.pawfaves.model.data.remote.repository.PawFavRepository

internal class BreedListVieModelFactory(
    private val pawFavRepository: PawFavRepository,
    private val pawFavesSharedPreferences: PawFavesSharedPreferences
) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(BreedListViewModel::class.java)) {
            return BreedListViewModel(pawFavRepository, pawFavesSharedPreferences) as T
        }
        throw IllegalArgumentException("Unknown HomeViewModel class")
    }
}
