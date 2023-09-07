package com.compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import App
import com.google.android.gms.ads.MobileAds

/**
 * Created by Ashwani Kumar Singh on 10,August,2023.
 */
class AppActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MobileAds.initialize(this) {}
        setContent { App() }
    }
}