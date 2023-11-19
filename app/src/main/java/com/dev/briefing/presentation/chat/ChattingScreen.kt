package com.dev.briefing.presentation.chat


import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.dev.briefing.BuildConfig
import com.dev.briefing.util.component.CommonHeader
import com.google.accompanist.web.AccompanistWebChromeClient
import com.google.accompanist.web.AccompanistWebViewClient
import com.google.accompanist.web.WebView
import com.google.accompanist.web.rememberWebViewState

@Composable
fun ChattingScreen(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit
) {
    val webViewState =
        rememberWebViewState(
            url = BuildConfig.WEB_URL + "storage",
            additionalHttpHeaders = emptyMap()
        )
    val webViewClient = AccompanistWebViewClient()
    val webChromeClient = AccompanistWebChromeClient()

    Column {
        Box(
            modifier = Modifier.padding(horizontal = 30.dp)
        ) {
            CommonHeader(
                onBackClick = onBackClick,
                header = "채팅 기록"
            )
        }
        WebView(
            modifier = Modifier.fillMaxHeight(),
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
        Spacer(modifier = Modifier.height(100.dp))
    }
}
