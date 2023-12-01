package com.app.storage

import android.content.SharedPreferences
import com.daniel_avila.AndroidApp
import com.russhwolf.settings.Settings
import com.russhwolf.settings.SharedPreferencesSettings

/**
 * Created by Ashwani Kumar Singh on 30,November,2023.
 */

actual val settings: Settings by lazy {
    val delegate: SharedPreferences = AndroidApp.instance.getSharedPreferences("settings", 0)
     SharedPreferencesSettings(delegate)
}