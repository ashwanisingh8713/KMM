package com.app


class BrowserPlatform: Platform {
    override val name: String
        get() = "Browser"
}
actual fun getScreenSize(): Platform {
    return BrowserPlatform()
}