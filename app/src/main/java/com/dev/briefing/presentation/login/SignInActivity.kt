package com.dev.briefing.presentation.login

import SignInScreen
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.dev.briefing.presentation.theme.BriefingTheme
class SignInActivity: ComponentActivity() {
    @Override
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("SignInActivity", "onCreate: ")
        setContent {
            BriefingTheme {
                // A surface container using the 'background' color from the theme
                SignInScreen()
            }
        }
    }
}