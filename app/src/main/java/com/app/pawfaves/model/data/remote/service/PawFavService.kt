package com.app.pawfaves.model.data.remote.service

import com.app.pawfaves.model.data.entities.AllBreedsResponse
import com.app.pawfaves.model.data.entities.BreedsResponse
import com.app.pawfaves.model.data.entities.ByBreedResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

internal interface PawFavService {
    @GET("breeds/list/all")
    suspend fun getAllBreeds(): Response<AllBreedsResponse>

    @GET("breed/{breed}/images")
    suspend fun getListByBreed(@Path("breed") breed: String): Response<ByBreedResponse>

    @GET("breeds/image/random")
    suspend fun getRandom(): Response<BreedsResponse>

}