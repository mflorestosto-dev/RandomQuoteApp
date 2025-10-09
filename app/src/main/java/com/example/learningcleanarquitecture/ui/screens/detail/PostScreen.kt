package com.example.learningcleanarquitecture.ui.screens.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.learningcleanarquitecture.data.model.PostModel

@Composable
fun PostScreen(viewModel: PostViewModel = viewModel()) {

    val post by viewModel.post.observeAsState(initial = null)
    val isLoading by viewModel.isLoading.observeAsState(initial = false)

    LaunchedEffect(key1 = Unit) {
        viewModel.onCreate()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .clickable(enabled = !isLoading) {
                viewModel.randomPost()
            },
        contentAlignment = Alignment.Center
    ) {
        if (isLoading == true) {
            CircularProgressIndicator()
        } else {
            post?.let {
                PostContent(post = it)
            } ?: run {
                Text(
                    text = "Toca la pantalla para cargar un post.",
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color.Black
                )
            }
        }
    }
}

@Composable
fun PostContent(post: PostModel) {
    Column(
        modifier = Modifier
            .padding(24.dp)
            .fillMaxSize(),
        Arrangement.Center
    ) {
        Text(
            text = post.title,
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
        Spacer(modifier = Modifier.height(12.dp))
        Text(
            text = post.body,
            fontSize = 16.sp,
            lineHeight = 22.sp,
            color = Color.Black
        )
    }
}

@Preview(showBackground = true, name = "Screen with Post")
@Composable
fun PostScreenPreview() {
    MaterialTheme {
        val samplePost = PostModel(
            id = 1,
            userId = 1,
            title = "Título de Ejemplo",
            body = "Este es el cuerpo del post de ejemplo para la previsualización. Toca para ver otro."
        )
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            PostContent(post = samplePost)
        }
    }
}

@Preview(showBackground = true, name = "Loading Screen")
@Composable
fun PostScreenLoadingPreview() {
    MaterialTheme {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    }
}

@Preview(showBackground = true, name = "Initial Screen")
@Composable
fun PostScreenInitialPreview() {
    MaterialTheme {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text(
                text = "Toca la pantalla para cargar un post.",
                style = MaterialTheme.typography.bodyLarge,
                color = Color.Black
            )
        }
    }
}
