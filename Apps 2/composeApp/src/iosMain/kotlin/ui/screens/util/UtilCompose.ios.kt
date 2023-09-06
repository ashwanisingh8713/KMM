package ui.screens.util

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.interop.UIKitView
import androidx.compose.ui.unit.dp
import platform.WebKit.WKWebView

/**
 * Created by Ashwani Kumar Singh on 06,September,2023.
 */

@Composable
actual fun htmlDescription(description: String, modifier: androidx.compose.ui.Modifier) {
    val uu = remember { WKWebView() }
    UIKitView(
        modifier = modifier.width(300.dp).height(300.dp),
        factory = {
            uu
        },
        update = {
            it.loadHTMLString(
                "<html><body>$description</body></html>",
                baseURL = null
            )
        }
    )
}