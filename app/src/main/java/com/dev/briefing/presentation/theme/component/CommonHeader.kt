package com.dev.briefing.presentation.theme.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dev.briefing.R
import androidx.constraintlayout.compose.ConstraintLayout
import com.dev.briefing.presentation.theme.BriefingTheme
import java.time.format.TextStyle

@Preview
@Composable
fun PreviewCommonHeader() {
    CommonHeader(
        onBackClick = {},
        header = "Briefing Premium",
        color = BriefingTheme.color.BackgroundWhite
    )
}


@Composable
fun CommonHeader(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit = {},
    header: String = "",
    color: Color = BriefingTheme.color.BackgroundWhite,
) {
    Box(modifier.fillMaxWidth().background(color).padding(20.dp, 16.dp)) {
        Box(modifier = Modifier
            .size(33.dp)
            .clickable(onClick = onBackClick)
            .align(Alignment.CenterStart)) {
            Image(
                modifier = Modifier
                    .size(8.dp, 16.dp)
                    .align(Alignment.Center),
                painter = painterResource(
                    id = R.drawable.vector
                ),
                contentDescription = "뒤로가기"
            )
        }

        Text(
            text = header,
            modifier = Modifier
                .align(Alignment.Center),
            style = BriefingTheme.typography.TitleStyleRegular.copy(
                fontSize = 20.sp,
                fontWeight = FontWeight(400),
                color = Color(0xFF000000),
                textAlign = TextAlign.Center,
            )
        )
    }
}