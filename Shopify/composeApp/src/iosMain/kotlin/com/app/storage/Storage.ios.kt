package com.app.storage

import com.russhwolf.settings.NSUserDefaultsSettings
import com.russhwolf.settings.Settings
import platform.Foundation.NSUserDefaults

/**
 * Created by Ashwani Kumar Singh on 30,November,2023.
 */


actual val settings: Settings by lazy {

    val delegate: NSUserDefaults by lazy {
        NSUserDefaults.standardUserDefaults
    }

    NSUserDefaultsSettings(delegate)
}