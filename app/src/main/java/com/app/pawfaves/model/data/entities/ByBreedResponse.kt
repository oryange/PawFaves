package com.app.pawfaves.model.data.entities

import com.google.gson.annotations.SerializedName

data class ByBreedResponse(
    @SerializedName("message")
    val listOfmessage: List<String>,
)