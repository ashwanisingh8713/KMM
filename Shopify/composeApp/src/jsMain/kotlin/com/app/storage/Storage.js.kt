package com.app.storage

import com.russhwolf.settings.Settings
import com.russhwolf.settings.StorageSettings

actual val settings: Settings by lazy {
    val settings: Settings = StorageSettings()
    settings
}

