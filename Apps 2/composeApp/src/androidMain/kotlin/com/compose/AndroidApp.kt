package com.compose

import android.app.Application
import android.content.Context
import android.content.pm.ApplicationInfo
import di.initKoin
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.logger.Level

/**
 * Created by Ashwani Kumar Singh on 10,August,2023.
 */

class AndroidApp : Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin {
            androidLogger(if (isDebug()) Level.ERROR else Level.NONE)
            androidContext(this@AndroidApp)
        }
    }
}

fun Context.isDebug() = 0 != applicationInfo.flags and ApplicationInfo.FLAG_DEBUGGABLE

