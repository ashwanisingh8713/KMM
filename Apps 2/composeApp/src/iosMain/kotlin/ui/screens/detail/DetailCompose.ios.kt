package ui.screens.detail

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.interop.UIKitView
import domain.mapper.ArticleMapper
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.readValue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import platform.CoreGraphics.CGRectZero
import platform.WebKit.WKWebView
import platform.WebKit.WKWebViewConfiguration
import ui.sharedui.DetailBanner

/**
 * Created by Ashwani Kumar Singh on 15,September,2023.
 */

@Composable
actual fun DetailPageCompose(article: ArticleMapper, modifier: Modifier) {
    Surface {
    Column(modifier = Modifier.fillMaxSize()

        ) {
            // Showing Banner
            DetailBanner(article, Modifier.fillMaxWidth().fillMaxHeight(0.4f))
            // Showing HTML Description
            HtmlDescription(article.de!!, modifier = Modifier.fillMaxSize())
            // Showing Taboola Widgets
            LoadTaboolaWidget(
                pageUrl = article.al!!,
                modifier = Modifier.fillMaxWidth().fillMaxHeight(.1f)
            )
    }
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