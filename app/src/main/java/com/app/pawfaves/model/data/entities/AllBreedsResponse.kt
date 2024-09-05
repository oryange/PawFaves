package com.app.pawfaves.model.data.entities

import com.google.gson.annotations.SerializedName

data class AllBreedsResponse(
    @SerializedName("message")
    val messageObject: Map<String, Any>
)