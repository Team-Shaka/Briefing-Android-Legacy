package com.dev.briefing.ui.setting

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun SettingScreen (
    modifier: Modifier = Modifier
){
    Text(
        text = "Updated: 23.08.07 5AM",
        style = MaterialTheme.typography.labelMedium
    )
}