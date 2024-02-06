package com.app

import android.widget.Toast

/**
 * Created by Ashwani Kumar Singh on 04,December,2023.
 */

class AndroidPlatform: Platform {
    override val name: String
        get() = "Android"
}

actual fun getScreenSize(): Platform {
    return AndroidPlatform()
}

