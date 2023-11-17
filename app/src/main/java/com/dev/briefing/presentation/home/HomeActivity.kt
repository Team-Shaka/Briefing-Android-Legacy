package com.dev.briefing.presentation.home

import android.os.Bundle
import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
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
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeActivity : ComponentActivity() {

    private val homeViewModel : HomeViewModel by viewModel()

    private lateinit var requestPermissionLauncher: ActivityResultLauncher<Array<String>>

    @OptIn(ExperimentalPermissionsApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestPermissionLauncher =
            registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
                val deniedPermissions = permissions.entries.filter { !it.value }.map { it.key }

                if (deniedPermissions.isNotEmpty()) {
                    Log.d("HomeActivity", "Denied Permissions: $deniedPermissions")
                } else {
                    homeViewModel.setAlarm()
                    Log.d("HomeActivity", "All permissions are granted.")
                }
            }

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

    private fun requestNotificationPermission() {
        val permissions = mutableListOf<String>()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            permissions.add(Manifest.permission.POST_NOTIFICATIONS)
        }
        // All permissions that are needed are already granted
        if (permissions.isEmpty()) {
            Log.d("HomeActivity", "set alarm in permission check")
            homeViewModel.setAlarm()
            return
        }
        val permissionsArray = permissions.toTypedArray()
        requestPermissionLauncher.launch(permissionsArray)
    }
}