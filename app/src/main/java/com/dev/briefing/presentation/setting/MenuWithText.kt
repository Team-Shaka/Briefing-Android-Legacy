package com.dev.briefing.presentation.setting

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.dev.briefing.R
import com.dev.briefing.presentation.theme.MainPrimary2
import com.dev.briefing.presentation.theme.White

@Composable
fun menuWithText(
    @StringRes menu: Int = R.string.navigation_chat,
    onClick: () -> Unit = {},
    color: Color = MainPrimary2,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(color = White, shape = RoundedCornerShape(5.dp))
            .padding(horizontal = 12.dp, vertical = 12.dp)
            .clickable(onClick = onClick),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = stringResource(id = menu),
            style = MaterialTheme.typography.titleSmall.copy(
                fontWeight = FontWeight(400),
                color = color
            )
        )
    }
}