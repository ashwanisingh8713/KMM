package com.multiplatform.webview.web

import androidx.compose.foundation.background
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.interop.UIKitView
import com.multiplatform.webview.web.WebViewNavigator
import com.multiplatform.webview.web.WebViewState
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.readValue
import platform.CoreGraphics.CGRectZero
import platform.Foundation.setValue
import platform.UIKit.endEditing
import platform.WebKit.WKWebView
import platform.WebKit.WKWebViewConfiguration
import platform.WebKit.javaScriptEnabled

/**
 * iOS WebView implementation.
 */
@Composable
actual fun ActualWebView(
    state: WebViewState,
    modifier: Modifier,
    captureBackPresses: Boolean,
    navigator: WebViewNavigator,
    onCreated: () -> Unit,
    onDispose: () -> Unit,
) {
    IOSWebView(
        state = state,
        modifier = modifier,
        captureBackPresses = captureBackPresses,
        navigator = navigator,
        onCreated = onCreated,
        onDispose = onDispose,
    )
}

/**
 * iOS WebView implementation.
 */
@OptIn(ExperimentalForeignApi::class)
@Composable
fun IOSWebView(
    state: WebViewState,
    modifier: Modifier,
    captureBackPresses: Boolean,
    navigator: WebViewNavigator,
    onCreated: () -> Unit,
    onDispose: () -> Unit,
) {
    val observer =
        remember {
            WKWebViewObserver(
                state = state,
                navigator = navigator,
            )
        }
    val navigationDelegate = remember { WKNavigationDelegate(state, navigator) }

    UIKitView(
        factory = {
            val config =
                WKWebViewConfiguration().apply {
                    allowsInlineMediaPlayback = true
                    defaultWebpagePreferences.allowsContentJavaScript = state.webSettings.isJavaScriptEnabled
                    preferences.apply {
                        setValue(state.webSettings.allowFileAccessFromFileURLs, forKey = "allowFileAccessFromFileURLs")
                        javaScriptEnabled = state.webSettings.isJavaScriptEnabled
                    }
                    setValue(state.webSettings.allowUniversalAccessFromFileURLs, forKey = "allowUniversalAccessFromFileURLs")
                }
            WKWebView(
                frame = CGRectZero.readValue(),
                configuration = config,
            ).apply {
                userInteractionEnabled = captureBackPresses
                allowsBackForwardNavigationGestures = captureBackPresses
                customUserAgent = state.webSettings.customUserAgentString
                scrollView.setScrollEnabled(false)
                this.addObservers(
                    observer = observer,
                    properties =
                        listOf(
                            "estimatedProgress",
                            "title",
                            "URL",
                            "canGoBack",
                            "canGoForward",
                        ),
                )
                this.navigationDelegate = navigationDelegate
                onCreated()
            }.also { state.webView = IOSWebView(it) }
        },
        modifier = modifier,
        onRelease = {
            state.webView = null
            it.removeObservers(
                observer = observer,
                properties =
                    listOf(
                        "estimatedProgress",
                        "title",
                        "URL",
                        "canGoBack",
                        "canGoForward",
                    ),
            )
            it.navigationDelegate = null
            onDispose()
        },
    )
}
