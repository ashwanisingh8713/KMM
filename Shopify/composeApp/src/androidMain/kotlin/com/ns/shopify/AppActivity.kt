package com.ns.shopify

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import com.app.App

/**
 * Created by Ashwani Kumar Singh on 01,December,2023.
 */
class AppActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { App(isDark = isSystemInDarkTheme()) }
    }
}