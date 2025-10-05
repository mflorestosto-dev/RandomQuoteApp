package com.example.learningcleanarquitecture.ui.screens.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.learningcleanarquitecture.domain.GetPostsUseCase
import com.example.learningcleanarquitecture.data.model.PostModel
import com.example.learningcleanarquitecture.domain.GetRandomPostUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class PostViewModel: ViewModel() {

    val post = MutableLiveData<PostModel>()
    val isLoading = MutableLiveData<Boolean>()

    var getPostDetailUseCase = GetPostsUseCase()
    var getRandomPostDetailUseCase = GetRandomPostUseCase()


    fun onCreate() {
        viewModelScope.launch {
            isLoading.postValue(true)
            try {
                val result: List<PostModel> ?= getPostDetailUseCase()
                if (!result.isNullOrEmpty()) {
                    post.postValue(result[0])
                }
            } catch (e: Exception) {
                // Opcional: Manejar el error, por ejemplo, posteando un mensaje de error a otro LiveData
                // Log.e("PostViewModel", "Error fetching posts", e)
            } finally {
                isLoading.postValue(false)
            }
        }
    }


    fun randomPost() {
        viewModelScope.launch {
            isLoading.postValue(true)
            try {
                val result: PostModel? = getRandomPostDetailUseCase()
                if (result != null) {
                    post.postValue(result)
                }
            } catch (e: Exception) {
                // Opcional: Manejar el error, por ejemplo, posteando un mensaje de error a otro LiveData
                // Log.e("PostViewModel", "Error fetching random post", e)
            } finally {
                isLoading.postValue(false)
            }
        }
    }
}
