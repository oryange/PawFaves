package com.app.pawfaves.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.pawfaves.model.data.remote.repository.PawFavRepository
import com.app.pawfaves.utils.ResultState
import com.app.pawfaves.utils.UrlConstants.DEFAULT_VALUE
import kotlinx.coroutines.launch

internal class HomeViewModel(private val pawFavRepository: PawFavRepository) : ViewModel() {
    private val _randomDog = MutableLiveData<String>()
    private val _allBreedsList = MutableLiveData<List<String>>()

    val randomDog = _randomDog
    val allBreedsList = _allBreedsList

    fun getAllBreeds() {
        viewModelScope.launch {
            val response = pawFavRepository.getAllBreeds()
            response.let {
                when (it) {
                    is ResultState.Success -> {
                        _allBreedsList.postValue(it.data.messageObject.keys.toList())
                    }

                    is ResultState.Error -> _allBreedsList.postValue(listOf(DEFAULT_VALUE))
                }
            }
        }
    }

    fun getRandom() {
        viewModelScope.launch {
            val response = pawFavRepository.getRandom()
            response.let {
                when (it) {
                    is ResultState.Success -> _randomDog.postValue(it.data.message)
                    is ResultState.Error -> _randomDog.postValue(DEFAULT_VALUE)
                }
            }
        }
    }
}