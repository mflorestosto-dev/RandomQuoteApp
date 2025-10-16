package com.example.learningcleanarquitecture.domain

import com.example.learningcleanarquitecture.data.PostRepository
import com.example.learningcleanarquitecture.data.model.PostModel
import javax.inject.Inject

class GetPostsUseCase @Inject constructor(private val repository: PostRepository) {
    suspend operator fun invoke(): List<PostModel> ?= repository.getAllPosts()
}