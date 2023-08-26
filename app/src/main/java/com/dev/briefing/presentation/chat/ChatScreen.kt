package com.dev.briefing.presentation.chat

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dev.briefing.BuildConfig
import com.dev.briefing.R
import com.dev.briefing.presentation.theme.GradientEnd
import com.dev.briefing.presentation.theme.GradientStart
import com.dev.briefing.presentation.theme.MainPrimary
import com.dev.briefing.presentation.theme.MainPrimary2
import com.google.accompanist.web.AccompanistWebChromeClient
import com.google.accompanist.web.AccompanistWebViewClient
import com.google.accompanist.web.WebView
import com.google.accompanist.web.rememberWebViewState

@Composable
fun ChatScreen(
    modifier: Modifier = Modifier
) {
    val webViewState =
        rememberWebViewState(
            url = BuildConfig.WEB_URL + "briefChat",
            additionalHttpHeaders = emptyMap()
        )
    val webViewClient = AccompanistWebViewClient()
    val webChromeClient = AccompanistWebChromeClient()
//    Text(
//        text = "Updated: 23.0ho8.07 5AM",
//        style = MaterialTheme.typography.labelMedium
//    )
    Column {
        ChatHeader()
        WebView(
            state = webViewState,
            client = webViewClient,
            chromeClient = webChromeClient,
            onCreated = { webView ->
                with(webView) {
                    settings.run {
                        javaScriptEnabled = true
                        domStorageEnabled = true
                        javaScriptCanOpenWindowsAutomatically = false
                    }
                }
            }
        )
        Spacer(modifier = Modifier.height(200.dp))

    }

}

@Composable
fun ChatHeader(
    modifier: Modifier = Modifier,
    onScrapClick: () -> Unit = {},
) {

    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(color = MainPrimary2)
            .padding(horizontal = 27.dp, vertical = 32.dp),
        horizontalArrangement = Arrangement.SpaceBetween,

        ) {
        Text(
            text = stringResource(R.string.home_title),
            style = MaterialTheme.typography.titleMedium.copy(
                fontSize = 24.sp,
                fontWeight = FontWeight(400)
            )
        )

        Image(
            painter = painterResource(
                id = R.drawable.storage
            ),
            contentDescription = "채팅 저장 공간", modifier = Modifier
                .clickable(onClick = onScrapClick)
        )


    }
}