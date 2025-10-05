package com.example.learningcleanarquitecture.domain

import com.example.learningcleanarquitecture.data.PostRepository
import com.example.learningcleanarquitecture.data.model.PostModel
import com.example.learningcleanarquitecture.data.model.PostProvider

class GetPostsUseCase {
    private val repository = PostRepository()

    suspend operator fun invoke(): List<PostModel> ?= repository.getAllPosts()


}