plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    // CAMBIO: El plugin de Firebase App Distribution se declara aquí, no en las dependencias.
}

android {
    namespace = "com.example.learningcleanarquitecture"
    // CAMBIO 1: Se actualizó a 36 para solucionar el primer error que tuviste.
    compileSdk = 36

    defaultConfig {
        applicationId = "com.example.learningcleanarquitecture"
        minSdk = 24
        // CAMBIO 1: Es una buena práctica que targetSdk sea igual a compileSdk.
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        // CAMBIO 2: Se establece Java 8, el estándar más compatible para Android moderno.
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }

    // CAMBIO 3: Se agrega el bloque 'packaging' para solucionar los errores de archivos duplicados.
    packaging {
        resources.excludes.add("META-INF/INDEX.LIST")
        resources.excludes.add("META-INF/DEPENDENCIES")
        // Se añaden otras exclusiones comunes para evitar futuros problemas.
        resources.excludes.add("META-INF/LICENSE*")
        resources.excludes.add("META-INF/NOTICE*")
        resources.excludes.add("META-INF/AL*")
        resources.excludes.add("META-INF/LGPL*")
        resources.excludes.add("META-INF/io.netty.versions.properties")
    }
}

dependencies {

    // --- Retrofit y Coroutines ---
    // CAMBIO 4: Versiones actualizadas para mayor estabilidad y nuevas características.
    implementation("com.squareup.retrofit2:retrofit:2.11.0")
    implementation("com.squareup.retrofit2:converter-gson:2.11.0")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.8.1")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.8.1")

    // --- Lifecycle & ViewModel (Necesario para LiveData y ViewModel) ---
    // CAMBIO 4: Versiones actualizadas y sin duplicados.
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.8.2")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.8.2")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.8.2")
    implementation("androidx.activity:activity-ktx:1.9.0")

    // --- Compose ---
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation("androidx.compose.material:material-icons-extended") // No es necesario especificar versión si usas el BOM de Compose

    // --- Testing ---
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    // CAMBIO 5: La dependencia de Benchmark es para pruebas, no para la implementación principal.
    androidTestImplementation(libs.androidx.benchmark.common)


    // --- Debug ---
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    // CAMBIO 6: La dependencia de Firebase App Distribution fue movida a la sección de plugins.
     implementation(libs.firebase.appdistribution.gradle) // <-- Se eliminó de aquí
}