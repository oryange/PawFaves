package com.app.pawfaves.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.pawfaves.model.data.entities.MessageData
import com.app.pawfaves.model.data.local.PawFavesSharedPreferences
import com.app.pawfaves.model.data.remote.repository.PawFavRepository
import com.app.pawfaves.utils.ResultState
import kotlinx.coroutines.launch

internal class BreedListViewModel(
    private val pawFavRepository: PawFavRepository,
    private val pawFavesSharedPreferences: PawFavesSharedPreferences
) : ViewModel() {

    private val _byBreedsList = MutableLiveData<List<MessageData>>()
    val byBreedsList = _byBreedsList

    fun getListByBreed(breed: String) {
        viewModelScope.launch {
            val response = pawFavRepository.getListByBreed(breed)
            response.let {
                when (it) {
                    is ResultState.Success -> {
                        _byBreedsList.postValue(it.data.listOfmessage.map { item ->
                            MessageData(item, getFavorite().contains(item))
                        })
                    }
                    is ResultState.Error -> _byBreedsList.postValue(emptyList())
                }
            }
        }
    }

    fun setFavoriteItem(newItem: String) {
        if (getFavorite().contains(newItem)) removeFavorite(newItem)
        else {
            addFavorite(newItem)
        }
        verifyIsFavorite()
    }

     fun verifyIsFavorite() {
        _byBreedsList.value?.let { it ->
            _byBreedsList.value = it.onEach {
                it.isFavorite = getFavorite().contains(it.message)
            }
        }
    }

    private fun addFavorite(newItem: String): Boolean = pawFavesSharedPreferences.addFavorite(newItem)

    private fun removeFavorite(itemToRemove: String): Boolean =
        pawFavesSharedPreferences.removeFavorite(itemToRemove)

    private fun getFavorite(): List<String> = pawFavesSharedPreferences.getFavorite()
}
