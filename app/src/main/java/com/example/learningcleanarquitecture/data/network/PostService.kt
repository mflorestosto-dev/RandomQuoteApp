package com.example.learningcleanarquitecture.data.network

import com.example.learningcleanarquitecture.core.RetrofitHelper
import com.example.learningcleanarquitecture.data.model.PostModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class PostService {
    private val retrofit = RetrofitHelper.getRetrofit()

    suspend fun getPosts(): List<PostModel> {

        return withContext(Dispatchers.IO) {
            val response = retrofit.create(PostApiClient::class.java).getAllPosts()
            response.body() ?: emptyList()
        }

    }
}