package com.compose

import PianoTH
import android.app.Application
import android.content.Context
import android.content.pm.ApplicationInfo
import com.taboola.android.TBLPublisherInfo
import com.taboola.android.Taboola
import di.initKoin
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.logger.Level
import taboola.PublisherInfo

/**
 * Created by Ashwani Kumar Singh on 10,August,2023.
 */

class AndroidApp : Application() {

    companion object {
        lateinit var appContext: Context
    }

    override fun onCreate() {
        super.onCreate()
        appContext = applicationContext
        initKoin {
            androidLogger(if (isDebug()) Level.ERROR else Level.NONE)
            androidContext(this@AndroidApp)
        }

        // Define a publisher info object
        val publisherInfo = TBLPublisherInfo(PublisherInfo.PUBLISHER).setApiKey(PublisherInfo.API_KEY)

        // Initialize Taboola SDK as early as possible
        Taboola.init(publisherInfo)

        // Initialize Piano SDK
        PianoTH().initPianoTH(this as Context)

    }


}

fun Context.isDebug() = 0 != applicationInfo.flags and ApplicationInfo.FLAG_DEBUGGABLE

