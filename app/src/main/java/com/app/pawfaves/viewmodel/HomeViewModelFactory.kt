package com.app.pawfaves.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.app.pawfaves.model.data.remote.repository.PawFavRepository

internal class HomeViewModelFactory(private val pawFavRepository: PawFavRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(pawFavRepository) as T
        }
        throw IllegalArgumentException("Unknown HomeViewModel class")
    }
}