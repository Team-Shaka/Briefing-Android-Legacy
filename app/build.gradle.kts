import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.google.services)
    alias(libs.plugins.firebase.crashlytics)
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
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.lifecycle.runtime.compose)

    // Compose UI Library
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.tooling)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.material3.android)
    implementation(libs.androidx.constraintlayout.compose)
    implementation(libs.androidx.ui.tooling.android)

    // Jetpack Compose
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.lifecycle.viewmodel.compose)

    // WebView
    implementation(libs.accompanist.webview)
    implementation(libs.accompanist.permissions)

    // Glide
    implementation(libs.glide)
    annotationProcessor(libs.compiler)

    // Retrofit2
    implementation(libs.retrofit)
    implementation(libs.converter.gson)
    implementation(libs.okhttp)
    implementation(libs.logging.interceptor)
    implementation(libs.okhttp.urlconnection)

    // Navigation Library
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)

    // Dependency Injection
    implementation(libs.koin.android)
    implementation(libs.koin.core)
    implementation(libs.koin.androidx.compose)
    implementation(libs.androidx.runtime.livedata)

    // Firebase
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.crashlytics)

    // Google Play Services
    implementation(libs.play.services.ads)
    implementation(libs.play.services.auth)

    implementation(libs.googleid)

    // Others
    implementation(libs.androidx.work.runtime.ktx)
    implementation(libs.logger)

    implementation(libs.androidx.credentials)
    implementation(libs.androidx.credentials.play.services.auth)
}
