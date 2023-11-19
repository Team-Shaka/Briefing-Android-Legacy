package com.dev.briefing.presentation.setting.component

import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.dev.briefing.R
import com.dev.briefing.presentation.theme.Black
import com.dev.briefing.presentation.theme.MainPrimary3
import com.dev.briefing.presentation.theme.MainPrimary6
import com.dev.briefing.presentation.theme.White

enum class SettingMenuType {
    ARROW,
    TEXT,
    ARROW_WITH_TEXT
}

/**
 * @param value : 값(TEXT 타입일 경우 들어갈 값)
 * @param  : 화살표 유무
 * @param title : 타이틀
 * @param onClick : 클릭 이벤트
 */
@Composable
fun SettingMenuItem(
    modifier: Modifier = Modifier,
    type: SettingMenuType = SettingMenuType.ARROW,
    @StringRes title: Int = R.string.navigation_chat,
    titleColor: Color = Black,
    value: String = "",
    onClick: () -> Unit = {},
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(color = White)
            .padding(horizontal = 19.dp, vertical = 29.dp)
            .clickable(onClick = onClick),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = stringResource(id = title),
            style = MaterialTheme.typography.titleSmall.copy(
                fontWeight = FontWeight(400),
                color = titleColor
            )
        )
        when (type) {
            SettingMenuType.ARROW -> {
                Image(
                    painter = painterResource(id = R.drawable.left_arrow),
                    contentDescription = "더보기"
                )
            }

            SettingMenuType.TEXT -> {
                Text(
                    text = value,
                    style = MaterialTheme.typography.titleSmall.copy(
                        fontWeight = FontWeight(400),
                        color = MainPrimary6
                    )
                )
            }

            SettingMenuType.ARROW_WITH_TEXT -> {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = value,
                        style = MaterialTheme.typography.titleSmall.copy(
                            fontWeight = FontWeight(400),
                            color = MainPrimary3
                        )
                    )
                    Spacer(Modifier.width(11.dp))
                    Image(
                        painter = painterResource(id = R.drawable.left_arrow),
                        contentDescription = "더보기"
                    )
                }
            }
        }
    }
}