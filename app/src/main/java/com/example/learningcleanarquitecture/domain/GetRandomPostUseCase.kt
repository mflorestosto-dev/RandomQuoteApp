package com.example.learningcleanarquitecture.domain

import com.example.learningcleanarquitecture.data.model.PostModel
import com.example.learningcleanarquitecture.data.model.PostProvider
import javax.inject.Inject
//No utilizo directamente el repositorio ya que me generaria un
//ciclo infinito de dependencias y ademas genera un pequeño retraso
//para etse caso es mejor crea una especie de cache en memoria
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