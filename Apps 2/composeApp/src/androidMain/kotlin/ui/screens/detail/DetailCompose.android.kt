package ui.screens.detail

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import taboola.LoadSingleTaboola

/**
 * Created by Ashwani Kumar Singh on 15,September,2023.
 */

import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.input.pointer.PointerEvent
import androidx.compose.ui.input.pointer.PointerInputChange
import androidx.compose.ui.input.pointer.consumePositionChange
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.fragment.app.FragmentActivity
import com.app.compose.R
import domain.mapper.ArticleMapper
import io.piano.android.composer.ComposerException
import io.piano.android.composer.listeners.ShowTemplateListener
import io.piano.android.composer.model.Event
import io.piano.android.composer.model.ExperienceRequest
import io.piano.android.composer.model.events.ShowTemplate
import io.piano.android.composer.showtemplate.ShowTemplateController
import ui.sharedui.DetailBanner

@Composable
actual fun DetailPageCompose(article: ArticleMapper, modifier: Modifier, onWebPageTouch:()->Unit) {
    val context = LocalContext.current

    LazyColumn(modifier = modifier) {
        item {
            Column (modifier = modifier){
                // Piano Template
                PianoBlockerTemplate()
                // Showing Banner
                DetailBanner(article, Modifier.fillMaxWidth().fillMaxHeight(0.6f))
                // Showing HTML Description
                HtmlDescription(article.de!!, modifier =  modifier.padding(end = 20.dp), onWebPageTouch = onWebPageTouch)
                // Showing Taboola Widgets
                LoadTaboolaWidget(pageUrl = article.al!!, modifier = Modifier.fillMaxWidth().fillMaxHeight(.1f))

                LaunchedEffect(true) {
                    pianoExperience(context)
                }
            }
        }
    }
}



@SuppressLint("ClickableViewAccessibility")
@Composable
actual fun HtmlDescription(description: String, modifier: Modifier, onWebPageTouch:()->Unit) {
    // Adding a WebView inside AndroidView
    // with layout as full screen
    AndroidView( modifier = modifier,
        factory = {
            WebView(it).apply {
                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
                webViewClient = WebViewClient()

                // to play video on a web view
                settings.javaScriptEnabled = true
                settings.defaultFontSize = 30
                // to verify that the client requesting your web page is actually your Android app.
                settings.userAgentString = System.getProperty("http.agent") //Dalvik/2.1.0 (Linux; U; Android 11; M2012K11I Build/RKQ1.201112.002)
                settings.useWideViewPort = false

                // Bind JavaScript code to Android code
                // addJavascriptInterface(WebAppInterface(context,infoDialog), "Android")
                loadDataWithBaseURL(
                    null,
                    "<html><body>$description</body></html>",
                    "text/html",
                    "UTF-8",
                    null
                )
            }
        }, update = {
            it.setOnTouchListener{ v, event ->
                onWebPageTouch()
                false
            }
        })

}
@Composable
actual fun LoadTaboolaWidget(pageUrl: String, modifier: Modifier) {
    LoadSingleTaboola(pageUrl)
}

fun pianoExperience(context: Context) {

    val showTemplateListener = ShowTemplateListener { event: Event<ShowTemplate> ->
        event.eventData.delayBy.let {
            val showTemplateController = ShowTemplateController(event)
            val activity = context as FragmentActivity
//            val fragmentActivity = activity as FragmentActivity
            showTemplateController.show(activity)
        }
    }

    val experienceRequest = ExperienceRequest.Builder()
        .contentSection("Premium")
        .url("https://www.thehindu.com/news/national/karnataka/fed-up-of-being-at-the-mercy-of-mncs-farmers-in-karnataka-are-democratising-seed-production/article67302777.ece")
        .tags(
            (listOf(
                "Premium Articles",
                "Karnataka",
                "agriculture",
                "healthy lifestyle",
                "organic foods",
                "food",
                "food security",
                "business and finance"
            ))
        )
        .debug(true)
        .build()

    io.piano.android.composer.Composer.getInstance().getExperience(
        experienceRequest,
        listOf(showTemplateListener)
    ) { exception: ComposerException ->
        Toast.makeText(
            context,
            "[${Thread.currentThread().name}] ${exception.cause?.message ?: exception.message}",
            Toast.LENGTH_LONG
        ).show()

    }
}

@Composable
fun PianoBlockerTemplate() {
    AndroidView(
        factory = { context ->
            val view =
                LayoutInflater.from(context).inflate(R.layout.piano_blocker_layout, null, false)

            // do whatever you want...
            view // return the view
        },
        update = { view ->
            // Update the view
        }
    )
}

