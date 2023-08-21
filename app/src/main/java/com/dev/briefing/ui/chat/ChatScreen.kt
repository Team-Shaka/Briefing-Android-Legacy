package com.dev.briefing.ui.chat

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun ChatScreen (
    modifier: Modifier = Modifier
){
    Text(
        text = "Updated: 23.0ho8.07 5AM",
        style = MaterialTheme.typography.labelMedium
    )
}