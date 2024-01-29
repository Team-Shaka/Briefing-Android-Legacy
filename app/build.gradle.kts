import java.util.Properties

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.gms.google-services")
    id("com.google.firebase.crashlytics")
}

android {
    namespace = "com.dev.briefing"
    compileSdk = 34

    val keyProperties = Properties().apply {
        load(project.rootProject.file("key.properties").inputStream())
    }

    defaultConfig {
        applicationId = "com.dev.briefing"
        minSdk = 28
        targetSdk = 34
        versionCode = 5
        versionName = "2.0.1"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables.useSupportLibrary = true

        val properties = Properties().apply {
            load(project.rootProject.file("local.properties").inputStream())
        }

        buildConfigField("String", "BASE_URL", "\"${properties.getProperty("BASE_URL")}\"")
        buildConfigField("String", "WEB_URL", "\"${properties.getProperty("WEB_URL")}\"")
        buildConfigField("String", "NOTIFICATION_CHANNEL_ID", "\"NOTIFICATION_CHANNEL_ID\"")
        buildConfigField("String", "GOOGLE_CLIENT_ID", "\"${properties.getProperty("GOOGLE_CLIENT_ID")}\"")
        buildConfigField("String", "ADMOB_APP_ID", "\"${properties.getProperty("ADMOB_APP_ID")}\"")
        buildConfigField("String", "ADMOB_BANNER_ID", "\"${properties.getProperty("ADMOB_BANNER_ID")}\"")
        buildConfigField("String", "ADMOB_TEST_DEVICE_1", "\"${properties.getProperty("ADMOB_TEST_DEVICE_1")}\"")

        manifestPlaceholders["admobAppId"] = "${properties.getProperty("ADMOB_APP_ID")}"
    }

    signingConfigs {
        create("config") {
            keyAlias = keyProperties["keyAlias"] as String
            keyPassword = keyProperties["keyPassword"] as String
            storeFile = file(keyProperties["storeFile"] as String)
            storePassword = keyProperties["storePassword"] as String
        }
    }

    buildFeatures {
        buildConfig = true
        compose = true
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.3"
    }

    packagingOptions {
        resources {
            excludes.add("/META-INF/{AL2.0,LGPL2.1}")
        }
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    implementation ("androidx.core:core-ktx:1.8.0")
    implementation(platform(kotlin("bom", version = "1.8.0")))
    implementation ("androidx.lifecycle:lifecycle-runtime-ktx:2.6.2")

    implementation ("androidx.lifecycle:lifecycle-runtime-compose:2.6.0-beta01")
    //Compose UI Library
    implementation ("androidx.activity:activity-compose:1.8.0")
    implementation(platform("androidx.compose:compose-bom:2023.10.01"))
    implementation ("androidx.compose.ui:ui")
    implementation ("androidx.compose.ui:ui-graphics")
    implementation ("androidx.compose.ui:ui-tooling")
    implementation ("androidx.compose.material3:material3:1.2.0-alpha12")
    implementation ("androidx.compose.material3:material3-android:1.2.0-alpha12")


    implementation("androidx.constraintlayout:constraintlayout-compose:1.0.1")
    implementation ("androidx.compose.ui:ui-tooling-android:1.5.4")
    //Test Library
    testImplementation ("junit:junit:4.13.2")
    androidTestImplementation ("androidx.test.ext:junit:1.1.5")
    androidTestImplementation ("androidx.test.espresso:espresso-core:3.5.1")

    //WebView
    implementation ("com.google.accompanist:accompanist-webview:0.24.13-rc")
    //Notification
    implementation("com.google.accompanist:accompanist-permissions:0.31.1-alpha")
    //Glide
    implementation ("com.github.bumptech.glide:glide:4.14.2")
    annotationProcessor ("com.github.bumptech.glide:compiler:4.14.2")
    //Retrofit2
    implementation ("com.squareup.retrofit2:retrofit:2.9.0")
    implementation ("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation ("com.squareup.okhttp3:okhttp:4.9.0")
    implementation ("com.squareup.okhttp3:logging-interceptor:4.9.0")
    implementation ("com.squareup.okhttp3:okhttp-urlconnection:4.9.0")

    //Navigation Library
    implementation ("androidx.navigation:navigation-fragment-ktx:2.5.3")
    implementation ("androidx.navigation:navigation-ui-ktx:2.5.3")

    // Jetpack Compose Integration
    implementation ("androidx.navigation:navigation-compose:2.5.3")

    //ViewModel with Compose
    implementation ("androidx.lifecycle:lifecycle-viewmodel-compose:2.6.0-beta01")

    // Dependency Injection
    implementation ("io.insert-koin:koin-android:3.5.0")
    implementation ("io.insert-koin:koin-core:3.5.0")
    implementation ("io.insert-koin:koin-androidx-compose:3.5.0")
    implementation ("androidx.compose.runtime:runtime-livedata:1.3.2")

    //google social login
    implementation ("com.google.android.gms:play-services-auth:20.7.0")
    //analytics
    implementation(platform("com.google.firebase:firebase-bom:32.5.0"))
    implementation ("com.google.firebase:firebase-analytics")
    //crashlytics
    implementation("com.google.firebase:firebase-crashlytics")

    implementation ("androidx.work:work-runtime-ktx:2.8.0")

    implementation ("com.orhanobut:logger:2.2.0")

    implementation ("com.google.android.libraries.identity.googleid:googleid:1.1.0")

    implementation("androidx.credentials:credentials:1.2.0")
    implementation("androidx.credentials:credentials-play-services-auth:1.2.0")

    // Google Admob
    implementation ("com.google.android.gms:play-services-ads:22.6.0")
}