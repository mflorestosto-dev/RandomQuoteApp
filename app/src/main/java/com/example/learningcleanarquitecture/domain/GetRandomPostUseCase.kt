package com.example.learningcleanarquitecture.domain

import com.example.learningcleanarquitecture.data.PostRepository
import com.example.learningcleanarquitecture.data.model.PostModel
import com.example.learningcleanarquitecture.data.model.PostProvider

class GetRandomPostUseCase {
    private val repository = PostRepository()

    operator fun invoke(): PostModel? {
        val posts: List<PostModel> = PostProvider.posts
        if (!posts.isNullOrEmpty()) {
            val randomNumber = (posts.indices).random()
            return posts[randomNumber]
        }
        return null
    }
}