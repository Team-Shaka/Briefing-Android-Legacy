package com.dev.briefing.presentation.setting

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.dev.briefing.R
import com.dev.briefing.presentation.theme.BorderColor
import com.dev.briefing.presentation.theme.MainPrimary
import com.dev.briefing.presentation.theme.SubBackGround
import com.dev.briefing.presentation.theme.SubText2
import com.dev.briefing.presentation.theme.White

@Composable
fun SettingScreen(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit,
) {
    LazyColumn(

        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(color = SubBackGround)
            .padding(horizontal = 30.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        item{
            CommonHeader(onBackClick = onBackClick, header = "설정")
            Spacer(modifier = Modifier.height(50.dp))
            menuWithArrow(
                icon = R.drawable.setting_clock,
                menu = R.string.setting_alarm,
            )
            Spacer(modifier = Modifier.height(50.dp))
            menuWithArrow(
                icon = R.drawable.setting_version,
                menu = R.string.setting_version,
            )
            menuWithArrow(
                icon = R.drawable.setting_clock,
                menu = R.string.setting_feedback,
            )
            menuWithArrow(
                icon = R.drawable.setting_version_note,
                menu = R.string.setting_version_note,
            )
            Spacer(modifier = Modifier.height(50.dp))
            menuWithArrow(
                icon = R.drawable.setting_policy,
                menu = R.string.setting_policy,
            )
            Divider(
                color = BorderColor,
                thickness = 1.dp
            )
            menuWithArrow(
                icon = R.drawable.setting_policy,
                menu = R.string.setting_policy_private,
            )
            Divider(
                color = BorderColor,
                thickness = 1.dp
            )
            menuWithArrow(
                icon = R.drawable.setting_caution,
                menu = R.string.setting_caution,
            )
            Spacer(modifier = Modifier.height(50.dp))

            menuWithText(R.string.setting_logout)
            Divider(
                color = BorderColor,
                thickness = 1.dp
            )
            menuWithText(R.string.setting_signout)
            Spacer(modifier = Modifier.height(100.dp))

        }


    }
}

@Composable
fun menuWithText(
    @StringRes menu: Int = R.string.navigation_chat,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(color = White, shape = RoundedCornerShape(5.dp))
            .padding(horizontal = 12.dp, vertical = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = stringResource(id = menu),
            style = MaterialTheme.typography.titleSmall.copy(
                fontWeight = FontWeight(400),
                color = SubText2
            )
        )
    }
}

@Composable
fun menuWithArrow(
    @DrawableRes icon: Int = R.drawable.setting_caution,
    @StringRes menu: Int = R.string.navigation_chat,
    onClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(color = White, shape = RoundedCornerShape(5.dp))
            .padding(horizontal = 12.dp, vertical = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row {
            Image(
                painter = painterResource(id = icon),
                contentDescription = "더보기"
            )
            Spacer(modifier = Modifier.width(12.dp))
            Text(
                text = stringResource(id = menu),
                style = MaterialTheme.typography.titleSmall.copy(
                    fontWeight = FontWeight(400)
                )
            )
        }

        Image(
            painter = painterResource(id = R.drawable.left_arrow),
            contentDescription = "더보기"
        )

    }
}

@Composable
fun CommonHeader(
    onBackClick: () -> Unit,
    header: String = ""
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 60.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    )
    {
        Image(
            painter = painterResource(
                id = R.drawable.vector
            ),
            contentDescription = "뒤로가기", modifier = Modifier
                .clickable(onClick = onBackClick)
        )
        Text(
            text = header,
            style = MaterialTheme.typography.titleMedium.copy(
                color = MainPrimary,
                fontWeight = FontWeight(400)
            )
        )
        Text(
            text = "",
            style = MaterialTheme.typography.titleMedium.copy(
                color = MainPrimary
            )
        )

    }
}