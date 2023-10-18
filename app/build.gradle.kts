plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    kotlin("kapt")
    id("com.google.dagger.hilt.android")

}

android {
    namespace = "com.camihruiz24.cupcake"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.camihruiz24.cupcake"
        minSdk = 28
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.3"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.2")
    implementation("androidx.activity:activity-compose:1.8.0")
    implementation(platform("androidx.compose:compose-bom:2023.10.00"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")
    testImplementation(kotlin("test"))
    androidTestImplementation("androidx.test.ext:junit-ktx:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation(platform("androidx.compose:compose-bom:2023.10.00"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")


    // Navigation
    implementation("androidx.navigation:navigation-compose:2.7.4")

    // View Model integration in Compose
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.6.2")

    /**
     * TestNG is a testing framework inspired from JUnit and NUnit but introducing some
     * new functionalities that make it more powerful and easier to use.
     * It supports test configured by annotations, data-driven testing, parametric tests, etc.
     */
    testImplementation("org.testng:testng:6.9.6")

    // Retrofit
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    // OkHttp3 Logging Interceptor
    implementation("com.squareup.okhttp3:logging-interceptor:4.11.0")
    // Kotlinx.serialization
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.0")
    // Retrofit with Kotlin serialization Converter
    implementation("com.jakewharton.retrofit:retrofit2-kotlinx-serialization-converter:1.0.0")
    // OkHttp3 for logging interceptor
//    implementation("com.squareup.okhttp3:okhttp:4.11.0")

    // Coil for AsyncImage
    implementation("io.coil-kt:coil-compose:2.4.0")

    // Coroutines Testing Library
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.3")

    val hiltVersion = "2.48.1"
    // Dagger Hilt DI Library
    implementation("com.google.dagger:hilt-android:$hiltVersion")
    kapt("com.google.dagger:hilt-android-compiler:$hiltVersion")
    // Androidx Hilt
    implementation("androidx.hilt:hilt-navigation-compose:1.0.0")
    kapt("androidx.hilt:hilt-compiler:1.0.0")
    // Instrumentation tests for Hilt
    androidTestImplementation("com.google.dagger:hilt-android-testing:$hiltVersion")
    kaptAndroidTest("com.google.dagger:hilt-compiler:$hiltVersion")
    // Local unit tests for Hilt
    testImplementation ("com.google.dagger:hilt-android-testing:$hiltVersion")
    kaptTest("com.google.dagger:hilt-compiler:$hiltVersion")

}

// Allow references to generated code
kapt {
    correctErrorTypes = true
}