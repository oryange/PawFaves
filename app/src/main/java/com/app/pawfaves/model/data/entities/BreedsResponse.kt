package com.app.pawfaves.model.data.entities

import com.google.gson.annotations.SerializedName

data class BreedsResponse(
    @SerializedName("message")
    val message: String,
)