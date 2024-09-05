package com.app.pawfaves.model.data.remote.repository

import com.app.pawfaves.model.data.entities.AllBreedsResponse
import com.app.pawfaves.model.data.entities.BreedsResponse
import com.app.pawfaves.model.data.entities.ByBreedResponse
import com.app.pawfaves.utils.ResultState


internal interface PawFavRepository {

    suspend fun getAllBreeds(): ResultState<AllBreedsResponse>

    suspend fun getListByBreed(breed: String): ResultState<ByBreedResponse>

    suspend fun getRandom(): ResultState<BreedsResponse>
}