package com.app.pawfaves.model.data.remote

import com.app.pawfaves.model.data.remote.service.PawFavService
import com.app.pawfaves.utils.UrlConstants.BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import okhttp3.OkHttpClient

internal object RetrofitConfig {

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(OkHttpClient())
        .build()

    fun getApiService(): PawFavService = retrofit.create(PawFavService::class.java)
}