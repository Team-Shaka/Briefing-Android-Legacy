package com.dev.briefing.presentation.theme.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dev.briefing.R
import androidx.constraintlayout.compose.ConstraintLayout
import com.dev.briefing.presentation.theme.BriefingTheme

@Preview
@Composable
fun PreviewCommonHeader(){
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
    color: Color = BriefingTheme.color.BackgrundGray
) {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = color)
            .padding(top = 24.dp, bottom = 15.dp, start = 19.dp, end = 19.dp),
    )
    {
        val (backKey, title) = createRefs()
        Image(
            modifier = Modifier
                .height(20.dp)
                .clickable(onClick = onBackClick)
                .constrainAs(backKey) {
                    start.linkTo(parent.start)
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                },
            painter = painterResource(
                id = R.drawable.vector
            ),
            contentDescription = "뒤로가기"
        )
        Text(
            modifier = Modifier.constrainAs(title) {
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            },
            text = header,
            style = BriefingTheme.typography.SubtitleStyleBold.copy(
                color = BriefingTheme.color.TextBlack,
                fontSize = 20.sp,
                fontWeight = FontWeight(400)
            )
        )
    }
}