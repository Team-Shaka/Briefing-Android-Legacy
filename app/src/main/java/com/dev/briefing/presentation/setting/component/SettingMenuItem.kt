package com.dev.briefing.presentation.setting.component

import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.dev.briefing.R
import com.dev.briefing.presentation.theme.Black
import com.dev.briefing.presentation.theme.White
import androidx.compose.ui.graphics.ColorFilter
@Composable
fun SettingSection(
    @StringRes title: Int = R.string.navigation_chat,
) {
    Text(
        modifier = Modifier.padding(start = 26.dp, top = 26.dp, bottom = 8.dp),
        text = stringResource(id = title),
        style = MaterialTheme.typography.headlineLarge.copy(
            color = Black,
            fontWeight = FontWeight(400)
        )
    )
}

data class SettingMenu(
    val isArrow: Boolean,
    val text: String? = null,
    val value: String? = null,
)

/**
 * @param title : 타이틀
 * @param onClick : 클릭 이벤트
 */
@Composable
fun SettingMenuItem(
    modifier: Modifier = Modifier,
    type: SettingMenu,
    @StringRes title: Int,
    titleColor: Color = Black,
    onClick: () -> Unit = {},
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(color = White)
            .clickable(onClick = onClick)
            .padding(start = 26.dp, top = 19.dp, end = 19.dp, bottom = 19.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = stringResource(id = title),
            style = MaterialTheme.typography.headlineLarge.copy(
                fontWeight = FontWeight(400),
                color = titleColor
            )
        )
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            if (type.text != null) {
                Text(
                    text = type.text,
                    style = MaterialTheme.typography.headlineLarge.copy(
                        fontWeight = FontWeight(400),
                    )
                )
            }
            if (type.text != null && type.isArrow) {
                Spacer(Modifier.width(11.dp))
            }
            if (type.isArrow) {
                Image(
                    modifier = Modifier
                        .width(24.dp)
                        .height(24.dp),
                    painter = painterResource(id = R.drawable.left_arrow, ),
                    contentDescription = "더보기"
                )
            }
        }
    }
}