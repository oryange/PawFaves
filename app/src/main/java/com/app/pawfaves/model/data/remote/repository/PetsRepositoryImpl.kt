package com.app.pawfaves.model.data.remote.repository

import com.app.pawfaves.model.data.entities.AllBreedsResponse
import com.app.pawfaves.model.data.entities.BreedsResponse
import com.app.pawfaves.model.data.entities.ByBreedResponse
import com.app.pawfaves.model.data.remote.service.PawFavService
import com.app.pawfaves.utils.ResultState
import retrofit2.Response

internal class PawFavRepositoryImpl(private val pawFavService: PawFavService) : PawFavRepository {
    override suspend fun getAllBreeds(): ResultState<AllBreedsResponse> {
        return try {
            val response = pawFavService.getAllBreeds()
            if (response.isSuccessful) {
                response.body()?.let {
                    ResultState.Success(it)
                } ?: ResultState.Error("Response body is null")
            } else {
                ResultState.Error("Unsuccessful response from API")
            }
        } catch (e: Exception) {
            ResultState.Error("$ERROR_CALLING $e")
        }
    }

    override suspend fun getListByBreed(breed: String): ResultState<ByBreedResponse> {
        return handleApiCall { pawFavService.getListByBreed(breed) }

    }

    override suspend fun getRandom(): ResultState<BreedsResponse> {
        return handleApiCall { pawFavService.getRandom() }
    }

    private inline fun <T : Any> handleApiCall(apiCall: () -> Response<T>): ResultState<T> {
        return try {
            val response = apiCall()
            if (response.isSuccessful) {
                response.body()?.let {
                    ResultState.Success(it)
                } ?: ResultState.Error(RESPONSE_BODY_NULL)
            } else {
                ResultState.Error(UNSUCCESSFUL_RESPONSE)
            }
        } catch (e: Exception) {
            ResultState.Error("$ERROR_CALLING ${e.message}")
        }
    }

    companion object {
        private const val RESPONSE_BODY_NULL = "Response body is null"
        private const val UNSUCCESSFUL_RESPONSE = "Unsuccessful response from API"
        private const val ERROR_CALLING = "Error calling API:"
    }
}
