package com.dev.briefing.presentation.chat

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.dev.briefing.BuildConfig
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
}