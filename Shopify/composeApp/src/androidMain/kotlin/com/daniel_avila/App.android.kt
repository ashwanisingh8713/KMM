package com.daniel_avila

import android.app.Application
import android.content.Context
import android.content.pm.ApplicationInfo
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.app.App
import com.ns.shopify.di.shopifyInitKoin
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.logger.Level

class AndroidApp : Application() {

    companion object {
        lateinit var instance: AndroidApp
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        /*initKoin {
            androidLogger(if (isDebug()) Level.ERROR else Level.NONE)
            androidContext(this@AndroidApp)
        }*/

        shopifyInitKoin {
            androidLogger(if (isDebug()) Level.ERROR else Level.NONE)
            androidContext(this@AndroidApp)
        }
    }
}

fun Context.isDebug() = 0 != applicationInfo.flags and ApplicationInfo.FLAG_DEBUGGABLE

