plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.example.uas_mobile"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.uas_mobile"
        minSdk = 26
        targetSdk = 33
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

    buildFeatures{
        viewBinding = true
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures{
        dataBinding = true
        viewBinding = true
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.10.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.navigation:navigation-fragment-ktx:2.7.6")
    implementation("androidx.navigation:navigation-ui-ktx:2.7.6")
    implementation("com.google.android.material:material:1.10.0")

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")


    // Untuk melakukan perpindahan page
    implementation("androidx.viewpager2:viewpager2:1.0.0")


    // Untuk Font size & Layout size
    implementation ("com.intuit.sdp:sdp-android:1.1.0")
    // For SSP, use this:
    implementation ("com.intuit.ssp:ssp-android:1.1.0")

    // Untuk swiping button

    //Volley
    implementation("com.android.volley:volley:1.2.1")

    //carosel
    implementation ("com.github.sparrow007:carouselrecyclerview:1.2.6")
    implementation ("com.github.bumptech.glide:glide:4.16.0")
    //cardview
    implementation ("androidx.cardview:cardview:1.0.0")
    //jetbrain
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.5.2")
    //mysql
    implementation ("mysql:mysql-connector-java:8.0.26")


}