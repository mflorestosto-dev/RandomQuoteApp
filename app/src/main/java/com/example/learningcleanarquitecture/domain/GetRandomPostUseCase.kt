package com.example.learningcleanarquitecture.domain

import com.example.learningcleanarquitecture.data.model.PostModel
import com.example.learningcleanarquitecture.data.model.PostProvider
import javax.inject.Inject

class GetRandomPostUseCase @Inject constructor() {

    operator fun invoke(): PostModel? {
        val posts: List<PostModel> = PostProvider.posts
        if (!posts.isNullOrEmpty()) {
            val randomNumber = (posts.indices).random()
            return posts[randomNumber]
        }
        return null
    }
}