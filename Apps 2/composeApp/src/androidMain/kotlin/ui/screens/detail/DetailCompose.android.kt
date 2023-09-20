package ui.screens.detail

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import domain.model.Article
import taboola.LoadSingleTaboola

/**
 * Created by Ashwani Kumar Singh on 15,September,2023.
 */

import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.ui.viewinterop.AndroidView
import domain.mapper.ArticleMapper

@Composable
actual fun DetailPageCompose(article: ArticleMapper, modifier: Modifier) {
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        item {
            Column {
                HtmlDescription(article.de!!, modifier = Modifier)
                // Showing Taboola Widgets
                LoadTaboolaWidget(pageUrl = article.al!!, modifier = Modifier.fillMaxWidth().fillMaxHeight(.1f))
            }
        }
    }
}

@Composable
actual fun HtmlDescription(description: String, modifier: Modifier) {

    // Adding a WebView inside AndroidView
    // with layout as full screen
    AndroidView(
        factory = {
            WebView(it).apply {
                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
                webViewClient = WebViewClient()

                // to play video on a web view
                settings.javaScriptEnabled = true

                // to verify that the client requesting your web page is actually your Android app.
                settings.userAgentString = System.getProperty("http.agent") //Dalvik/2.1.0 (Linux; U; Android 11; M2012K11I Build/RKQ1.201112.002)

//                settings.useWideViewPort = true


                // Bind JavaScript code to Android code
//                addJavascriptInterface(WebAppInterface(context,infoDialog), "Android")




//                loadUrl(url)
                loadDataWithBaseURL(
                    null,
                    "<html><body>$description</body></html>",
                    "text/html",
                    "UTF-8",
                    null
                )
            }
        }, update = {

        })

}
@Composable
actual fun LoadTaboolaWidget(pageUrl: String, modifier: Modifier) {
    LoadSingleTaboola(pageUrl)
}

