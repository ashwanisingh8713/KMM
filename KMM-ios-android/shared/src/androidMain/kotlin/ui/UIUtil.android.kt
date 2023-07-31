package ui

/**
 * Created by Ashwani Kumar Singh on 31,July,2023.
 */



actual fun timestampMs(): Long {
    return System.currentTimeMillis()
}

actual fun getScreenWidth(): Int {

    return 360
}
actual fun getPlatform(): String {

    return "Android"
}
actual fun getModelName(): String {

    return "Samsung 249"
}
actual fun getOSVersion(): String {

    return "Android 13"
}