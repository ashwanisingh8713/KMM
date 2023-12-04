package com.app

/**
 * Created by Ashwani Kumar Singh on 04,December,2023.
 */

class IosPlatform: Platform {
    override val name: String
        get() = "Ios"
}

actual fun getScreenSize(): Platform {
    return IosPlatform()
}
