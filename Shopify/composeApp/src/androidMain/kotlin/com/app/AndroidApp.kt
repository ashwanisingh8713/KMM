package com.app

import android.app.Application
import android.content.Context
import android.content.pm.ApplicationInfo
import com.ns.shopify.di.shopifyInitKoin
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.logger.Level


/**
 * Created by Ashwani Kumar Singh on 03,December,2023.
 */
class AndroidApp : Application() {

    companion object {
        lateinit var instance: AndroidApp
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        shopifyInitKoin {
            androidLogger(if (isDebug()) Level.ERROR else Level.NONE)
            androidContext(this@AndroidApp)
        }
    }
}

fun Context.isDebug() = 0 != applicationInfo.flags and ApplicationInfo.FLAG_DEBUGGABLE

