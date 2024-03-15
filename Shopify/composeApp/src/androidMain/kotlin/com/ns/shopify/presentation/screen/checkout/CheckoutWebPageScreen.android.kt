package com.ns.shopify.presentation.screen.checkout

import android.util.Log
import android.view.ViewGroup
import android.webkit.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import com.app.printLog
import com.ns.shopify.presentation.componets.DefaultBackArrow
import com.ns.shopify.presentation.screen.cart.CartViewModel
import java.io.ByteArrayInputStream


actual class CheckoutWebPageScreen actual constructor(val checkoutWebUrl: String, val cartId: String): Screen {

    private var webView: WebView? = null


    @Composable
    override fun Content() {

        var isReloaded by remember { mutableStateOf(false) }

        val navigation = LocalNavigator.current

        val cartViewModel = getScreenModel<CartViewModel>()
        val cartBuyerIdentityState = cartViewModel.cartBuyerIdentityUpdateState.collectAsState()
        val cartQueryState = cartViewModel.cartQueryState.collectAsState()

        val onBackPress: () -> Unit = {
            cartViewModel.cartBuyerCancel()
            navigation?.pop()
        }

        LaunchedEffect(key1 = cartBuyerIdentityState.value.isLoaded) {
            webView?.apply { reload() }
        }

        val onWebPageFinished:(String)->Unit = {url->
            printLog("onWebPageFinished URL: $url")
            if(isReloaded == false && url.contains("https://quickstart-fe108883.myshopify.com/checkouts")) {
                isReloaded = true
                cartViewModel.cartBuyerIdentityUpdate(cartId)
            } else if(url.contains("thank_you")) {
                // Clear the cartId from Caching Manager
                cartViewModel.clearCartId()
                cartViewModel.cartBuyerCancel()
                cartViewModel.refreshCartQueryState()
            }

        }

        val paymentStatusListener: (statusCode: Int, msg: String)->Unit = {statusCode, msg ->

        }


        Scaffold(
            topBar = {
                Row(
                    modifier = Modifier.fillMaxWidth().height(56.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {

                        // Back Arrow
                        Box(
                            modifier = Modifier
                                .size(50.dp)
                                .padding(top = 10.dp, start = 10.dp)
                        ) {
                            DefaultBackArrow {
                                onBackPress()
                            }
                        }

                        // Page Title
                        Text(
                            text = "Checkout ",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                        )

                }

            },
            bottomBar = {

                Divider(thickness = 1.dp, color = Color.Gray)
            },
            content = {
                Box(modifier = Modifier.fillMaxSize().padding(it)) {
                    CheckoutWebPageCompose(onWebPageFinished, checkoutWebUrl, paymentStatusListener)
                }
            })

    }


    @Composable
    fun CheckoutWebPageCompose(onWebPageFinished:(String)->Unit, checkoutWebUrl: String, paymentStatusListener:(statusCode: Int, msg: String)->Unit) {

        val mutableStateTrigger = remember { mutableStateOf(false) }

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

                    settings.useWideViewPort = true

                    webViewClient = object : WebViewClient() {


                        override fun onReceivedError(view: WebView?, request: WebResourceRequest?, error: WebResourceError?) {
                            super.onReceivedError(view, request, error)

//                        loadURL = if(isOnline(context)){
//                            "file:///android_asset/404.html" // other error
//                        } else{
//                            "file:///android_asset/error.html" // no internet
//                        }

                            mutableStateTrigger.value = true

                        }



                        // Compose WebView Part 7 | Hide elements from web view
                        override fun onPageFinished(view: WebView, url: String) {
                            super.onPageFinished(view, url)
                            Log.i("CheckoutWebView", "onPageFinished: $url")
                            onWebPageFinished(url)
                        }

                    }

                    loadUrl(checkoutWebUrl)
                    webView = this
                }
            }, update = {
                webView = it

            })




    }

}



