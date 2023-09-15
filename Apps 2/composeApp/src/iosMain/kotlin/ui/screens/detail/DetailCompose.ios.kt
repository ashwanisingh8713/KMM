package ui.screens.detail

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.interop.UIKitView
import domain.model.Article
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.readValue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import platform.CoreGraphics.CGRectZero
import platform.WebKit.WKWebView
import platform.WebKit.WKWebViewConfiguration

/**
 * Created by Ashwani Kumar Singh on 15,September,2023.
 */

@Composable
actual fun DetailPageCompose(article: Article, modifier: Modifier) {
    Column(modifier = modifier) {
        HtmlDescription(article.de, modifier = Modifier)
        // Showing Taboola Widgets
        LoadTaboolaWidget(pageUrl = article.al, modifier = Modifier.fillMaxWidth().fillMaxHeight(.1f))
    }
}

@Composable
actual fun LoadTaboolaWidget(pageUrl: String, modifier: Modifier) {

}

@OptIn(ExperimentalForeignApi::class)
@Composable
actual fun HtmlDescription(description: String, modifier: Modifier) {
    var wkWebView by remember { mutableStateOf<WKWebView?>(null) }

    LaunchedEffect(Unit) {
        val webConfiguration = withContext(Dispatchers.Main) {
            WKWebViewConfiguration()
        }
        wkWebView = withContext(Dispatchers.Main) {
            WKWebView(frame = CGRectZero.readValue(), configuration = webConfiguration)
        }
    }

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        if (wkWebView != null) {
            UIKitView(
                modifier = modifier,
                factory = {
                    wkWebView!!
                },
                update = {
//                    val request = NSURLRequest(uRL = NSURL(string = url))
//                    wkWebView?.loadRequest(request)
                    wkWebView?.loadHTMLString(
                        "<html><body>$description</body></html>",
                        baseURL = null
                    )
                }
            )
        } else {
            CircularProgressIndicator()
        }
    }

}