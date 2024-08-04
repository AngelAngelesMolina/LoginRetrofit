plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("com.google.devtools.ksp")
    kotlin("kapt")
}

android {
    namespace = "com.example.loginretrofit"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.loginretrofit"
        minSdk = 24
        targetSdk = 34
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    viewBinding {
        enable = true
    }
}
var retrofitVersion = "2.9.0"
var lifecycleVersion = "2.6.1"
var coroutinesVersion = "1.7.3"
var lifeDataVersion = "2.8.3"
var glideVersion = "4.15.1"
var gsonVersion = "2.11.0"
val activityVersion = "1.9.1"
dependencies {

    implementation("androidx.core:core-ktx:1.13.1")
    implementation("androidx.appcompat:appcompat:1.7.0")
    implementation("com.google.android.material:material:1.12.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.2.1")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.6.1")

    //GLIDE
    implementation("com.github.bumptech.glide:glide:$glideVersion")
    ksp("com.github.bumptech.glide:ksp:$glideVersion")

    //Retrofit
    implementation("com.squareup.retrofit2:retrofit:$retrofitVersion")
    implementation("com.squareup.retrofit2:converter-gson:$retrofitVersion")
    //GSON  implementation 'com.google.code.gson:gson:2.10.0'
    implementation("com.google.code.gson:gson:$gsonVersion")
    //COROUTINES
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:$coroutinesVersion")
    //LIVEDATA
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:$lifeDataVersion")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.8.4")
    // Kotlin
    implementation("androidx.activity:activity-ktx:$activityVersion")

}