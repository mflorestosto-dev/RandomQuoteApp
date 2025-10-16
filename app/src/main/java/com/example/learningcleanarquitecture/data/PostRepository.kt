package com.example.learningcleanarquitecture.data

import com.example.learningcleanarquitecture.data.model.PostModel
import com.example.learningcleanarquitecture.data.model.PostProvider
import com.example.learningcleanarquitecture.data.network.PostService
import javax.inject.Inject

class PostRepository @Inject constructor(private val api: PostService) {

    suspend fun getAllPosts(): List<PostModel> {
        val response: List<PostModel> = api.getPosts()
        PostProvider.posts = response
        return response.ifEmpty { emptyList() }
    }
}
