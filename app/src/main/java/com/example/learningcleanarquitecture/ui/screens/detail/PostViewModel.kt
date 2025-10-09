package com.example.learningcleanarquitecture.ui.screens.detail

import androidx.lifecycle.LiveData
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

    private val _post = MutableLiveData<PostModel?>()
    val post: LiveData<PostModel?> = _post

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading


    var getPostDetailUseCase = GetPostsUseCase()
    var getRandomPostDetailUseCase = GetRandomPostUseCase()


    fun onCreate() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val result: List<PostModel> ?= getPostDetailUseCase()
                if (!result.isNullOrEmpty()) {
                    _post.value = result[0]
                }
            } catch (e: Exception) {
                // Opcional: Manejar el error, por ejemplo, posteando un mensaje de error a otro LiveData
                // Log.e("PostViewModel", "Error fetching posts", e)
            } finally {
                _isLoading.value = false
            }
        }
    }


    fun randomPost() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val result: PostModel? = getRandomPostDetailUseCase()
                if (result != null) {
                    _post.value = result
                }
            } catch (e: Exception) {
                // Opcional: Manejar el error, por ejemplo, posteando un mensaje de error a otro LiveData
                // Log.e("PostViewModel", "Error fetching random post", e)
            } finally {
                _isLoading.value = false
            }
        }
    }
}
