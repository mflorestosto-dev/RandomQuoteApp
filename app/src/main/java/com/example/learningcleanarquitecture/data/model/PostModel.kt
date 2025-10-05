package com.example.learningcleanarquitecture.data.model

import com.google.gson.annotations.SerializedName

data class PostModel(
    @SerializedName("id")val id: Int,
    @SerializedName("userId")val userId: Int,
    @SerializedName("title")val title: String,
    @SerializedName("body")val body: String
)