package ui

import android.content.res.Configuration
import android.graphics.Bitmap
import android.util.Log
import android.view.ViewGroup
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebResourceResponse
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import java.io.BufferedReader
import java.io.ByteArrayInputStream
import java.io.IOException
import java.io.InputStreamReader
import java.util.UUID

/**
 * Created by Ashwani Kumar Singh on 31,July,2023.
 */



actual fun timestampMs(): Long {
    return System.currentTimeMillis()
}

actual fun getScreenWidth(): Int {

    return 360
}
actual fun getPlatform(): String {

    return "Android"
}
actual fun getModelName(): String {

    return "Samsung 249"
}
actual fun getOSVersion(): String {

    return "Android 13"
}

actual fun randomUUID() = UUID.randomUUID().toString()

@Composable
actual fun htmlDescription(description: String, modifier: Modifier) {

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
