// Define el paquete donde se encuentra este archivo.
package com.example.learningcleanarquitecture.ui.screens.detail

// Importaciones necesarias de Jetpack Compose para construir la UI.
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
// Importación para poder observar LiveData.
import androidx.lifecycle.Observer
// Importación del modelo de datos que define la estructura de un "Post".
import com.example.learningcleanarquitecture.data.model.PostModel

/**
 * Define la pantalla principal. Esta es una función "Composable", lo que significa
 * que describe una parte de la interfaz de usuario.
 * @param postViewModel Una instancia del ViewModel que maneja la lógica y los datos de esta pantalla.
 * Por defecto, crea una nueva instancia si no se le pasa ninguna.
 */
@Composable
fun PostScreen(
    postViewModel: PostViewModel = PostViewModel()
) {
    // ---- ESTADO DE LA UI ----
    // 'post' guardará el post actual que se muestra. 'remember' hace que el estado sobreviva a las recomposiciones.
    var post by remember { mutableStateOf<PostModel?>(null) }
    // 'isLoading' es un booleano para saber si se está cargando un nuevo post.
    var isLoading by remember { mutableStateOf(false) }

    // ---- OBSERVACIÓN DEL VIEWMODEL ----
    // 'DisposableEffect' es ideal para registrar y limpiar observadores (o cualquier recurso).
    // Se ejecuta cuando el Composable entra en la pantalla y se limpia cuando sale.
    DisposableEffect(postViewModel) {
        // Se crea un observador para el LiveData del post. Cuando el LiveData cambia, actualiza el estado 'post'.
        val postObserver = Observer<PostModel> { newPost -> post = newPost }
        // Se crea un observador para el estado de carga.
        val loadingObserver = Observer<Boolean> { loading -> isLoading = (loading == true) }

        // Se suscribe a los LiveData del ViewModel para recibir actualizaciones.
        // 'observeForever' se usa aquí porque 'DisposableEffect' nos da control sobre cuándo dejar de observar.
        postViewModel.post.observeForever(postObserver)
        postViewModel.isLoading.observeForever(loadingObserver)

        // 'onDispose' es la función de limpieza. Se ejecuta cuando el Composable es removido de la pantalla.
        // Es crucial para evitar fugas de memoria (memory leaks).
        onDispose {
            postViewModel.post.removeObserver(postObserver)
            postViewModel.isLoading.removeObserver(loadingObserver)
        }
    }

    // ---- CARGA INICIAL DE DATOS ----
    // 'LaunchedEffect' ejecuta un bloque de código (una corrutina) de forma segura dentro de un Composable.
    // Con 'key1 = Unit', se asegura de que este bloque se ejecute UNA SOLA VEZ cuando el Composable aparece por primera vez.
    LaunchedEffect(key1 = Unit) {
        // Llama a la función del ViewModel que se encarga de cargar el primer post.
        postViewModel.onCreate()
    }

    // ---- DISEÑO DE LA UI (Layout) ----
    // 'Box' es un contenedor que permite apilar elementos uno encima de otro.
    Box(
        modifier = Modifier
            .fillMaxSize() // Ocupa todo el espacio disponible.
            .background(Color.White) // Fondo blanco.
            .clickable(onClick = { // Hace que toda la pantalla sea clickeable.
                // Solo permite cargar un nuevo post si no hay uno ya cargándose.
                if (isLoading == false) {
                    postViewModel.randomPost() // Llama a la función para obtener un post aleatorio.
                }
            }),
        contentAlignment = Alignment.Center // Centra el contenido dentro del Box.
    ) {
        // ---- LÓGICA DE VISUALIZACIÓN ----
        // Si está cargando, muestra un indicador de progreso circular.
        if (isLoading == true) {
            CircularProgressIndicator()
        } else {
            // Si no está cargando, comprueba si hay un post para mostrar.
            // 'post?.let' ejecuta el bloque de código solo si 'post' no es nulo.
            post?.let {
                // Si hay un post, llama a otro Composable para mostrarlo.
                PlainPostText(post = it)
            } ?: run {
                // Si 'post' es nulo (estado inicial), muestra un texto de bienvenida.
                Text(
                    text = "Toca la pantalla para cargar un post.",
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color.Black
                )
            }
        }
    }
}

/**
 * Un Composable simple y "tonto" (stateless) que solo se encarga de mostrar
 * el texto de un post que recibe como parámetro.
 * @param post El objeto PostModel que contiene los datos a mostrar.
 */
@Composable
fun PlainPostText(post: PostModel) {
    // 'Column' apila los elementos verticalmente.
    Column(
        modifier = Modifier
            .padding(24.dp) // Añade un margen interno.
            .fillMaxSize(), // Ocupa todo el espacio.
    ) {
        // Muestra el título del post.
        Text(
            text = post.title,
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
        // Un espaciador vertical.
        Spacer(modifier = Modifier.height(12.dp))
        // Muestra el cuerpo del post.
        Text(
            text = post.body,
            fontSize = 16.sp,
            lineHeight = 22.sp, // Aumenta el espacio entre líneas para mejor legibilidad.
            color = Color.Black
        )
    }
}

// ---- PREVISUALIZACIONES ----
// La anotación '@Preview' permite ver cómo se ve el Composable en el editor de Android Studio
// sin necesidad de ejecutar la aplicación en un emulador o dispositivo.

// Muestra una previsualización de la pantalla con un post de ejemplo.
@Preview(showBackground = true)
@Composable
fun PostScreenPreview() {
    MaterialTheme {
        val samplePost = PostModel(id = 1, userId = 1, title = "Título de Ejemplo", body = "Este es el cuerpo del post de ejemplo para la previsualización. Toca para ver otro.")
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            PlainPostText(post = samplePost)
        }
    }
}

// Muestra una previsualización de la pantalla en su estado de "cargando".
@Preview(showBackground = true)
@Composable
fun PostScreenLoadingPreview() {
    MaterialTheme {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    }
}
