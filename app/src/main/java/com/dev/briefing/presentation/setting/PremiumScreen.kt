package com.dev.briefing.presentation.setting

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.dev.briefing.R
import com.dev.briefing.presentation.setting.component.PremiumText
import com.dev.briefing.presentation.theme.component.CommonHeader
import com.dev.briefing.presentation.setting.component.PremiumFunctionItem
import com.dev.briefing.presentation.setting.component.PremiumPriceInfo
import com.dev.briefing.presentation.setting.component.PremiumTextWithTitle
import com.dev.briefing.presentation.theme.Black
import com.dev.briefing.presentation.theme.SubText2
import com.dev.briefing.presentation.theme.Typography
import com.dev.briefing.presentation.theme.White

val _functions = listOf(
    R.string.premium_function_1,
    R.string.premium_function_2,
    R.string.premium_function_3,
    R.string.premium_function_4,
    R.string.premium_function_5,
)

@Composable
fun PremiumScreen(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit = {},
) {
    val context = LocalContext.current

    Column {
        CommonHeader(header = "Briefing Premium", color = White, onBackClick = onBackClick)
        LazyColumn(
            modifier = modifier
                .background(color = White)
                .padding(horizontal = 30.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            item {
                Spacer(modifier = Modifier.height(30.dp))
                Image(
                    modifier = Modifier
                        .width(73.dp),
                    painter = painterResource(id = R.drawable.ic_logo_items),
                    contentDescription = "로고"
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    PremiumText
                )
                Spacer(modifier = Modifier.height(40.dp))
                Text(
                    //                modifier = Modifier.align(Alignment.Start),
                    text = PremiumTextWithTitle
                )
                Text(
                    modifier = Modifier.padding(top = 33.dp, bottom = 21.dp),
                    text = stringResource(id = R.string.premium_function_title),
                    style = Typography.headlineLarge.copy(
                        fontWeight = FontWeight(400),
                        color = Black
                    ),
                )
                //기능 리스트
                Column(
                    horizontalAlignment = Alignment.Start,
                ) {
                    for (i in 0..4)
                        PremiumFunctionItem(
                            text = _functions[i]
                        )
                }
                Spacer(modifier = Modifier.height(17.dp))
                PremiumPriceInfo(
                    title = stringResource(id = R.string.premium_yearly_title),
                    subtitle = stringResource(id = R.string.premium_yearly_subtitle),
                    price = R.string.premium_yearly_price,
                    subInfo = R.string.premium_yearly_discount
                )
                Spacer(modifier = Modifier.height(15.dp))
                PremiumPriceInfo(
                    title = stringResource(id = R.string.premium_monthly_title),
                    subtitle = stringResource(id = R.string.premium_monthly_subtitle),
                    price = R.string.premium_monthly_price,
                )
                Spacer(modifier = Modifier.height(15.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Start,
                ) {
                    Text(
                        modifier = Modifier.clickable {
                            val intent =
                                Intent(
                                    Intent.ACTION_VIEW,
                                    // TODO: change url
                                    Uri.parse("https://sites.google.com/view/brieifinguse/%ED%99%88")
                                )
                            ContextCompat.startActivity(context, intent, null)
                        },
                        text = stringResource(id = R.string.premium_policy_service),
                        style = Typography.bodyMedium.copy(color = Black),
                        textDecoration = TextDecoration.Underline
                    )
                    Spacer(modifier = Modifier.width(17.dp))
                    Text(
                        modifier = Modifier.clickable {
                            val intent =
                                Intent(
                                    Intent.ACTION_VIEW,
                                    // TODO: change url
                                    Uri.parse("https://sites.google.com/view/brieifinguse/%ED%99%88")
                                )
                            ContextCompat.startActivity(context, intent, null)
                        },
                        text = stringResource(id = R.string.premium_policy_private),
                        style = Typography.bodyMedium.copy(color = Black),
                        textDecoration = TextDecoration.Underline
                    )
                }
                Text(
                    modifier = Modifier.padding(top = 20.dp),
                    text = stringResource(id = R.string.premium_policy_1),
                    style = Typography.bodyMedium.copy(
                        fontWeight = FontWeight(400),
                        color = SubText2
                    ),
                )
                Text(
                    modifier = Modifier.padding(top = 20.dp),
                    text = stringResource(id = R.string.premium_policy_2),
                    style = Typography.bodyMedium.copy(
                        fontWeight = FontWeight(400),
                        color = SubText2
                    ),
                )
                Spacer(modifier = Modifier.height(40.dp))

            }
        }
    }
}

@Preview
@Composable
fun PremiumScreenPreview() {
    PremiumScreen(modifier = Modifier)
}