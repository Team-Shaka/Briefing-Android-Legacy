package com.dev.briefing.presentation.home

import android.os.Bundle
import android.Manifest
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.core.app.ActivityCompat

import androidx.navigation.compose.rememberNavController

import com.dev.briefing.navigation.RootScreen
import com.dev.briefing.presentation.theme.BriefingTheme
import com.dev.briefing.util.NOTIFICATION_REQUEST_CODE
import com.google.accompanist.permissions.ExperimentalPermissionsApi

class HomeActivity : ComponentActivity() {
    @OptIn(ExperimentalPermissionsApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("HomeActivity", "onCreate: ")

        requestNotificationPermission()
        setContent {
            BriefingTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    RootScreen(navController = rememberNavController())
                }
            }
        }
    }

    fun requestNotificationPermission() {
        val permissions: Array<String> = arrayOf(
            Manifest.permission.POST_NOTIFICATIONS,
            Manifest.permission.SET_ALARM,
            Manifest.permission.SCHEDULE_EXACT_ALARM
        )
        ActivityCompat.requestPermissions(this, permissions, NOTIFICATION_REQUEST_CODE);
    }
}




