package com.example.learningcleanarquitecture.data.network

import com.example.learningcleanarquitecture.data.model.PostModel
import retrofit2.http.GET
import retrofit2.Response

interface PostApiClient {
    @GET("/posts")
    suspend fun getAllPosts(): Response<List<PostModel>>
}