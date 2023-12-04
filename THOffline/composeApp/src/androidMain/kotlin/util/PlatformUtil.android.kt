package util

/**
 * Created by Ashwani Kumar Singh on 20,September,2023.
 */

actual class PlatformUtil() {
    actual fun getPlatformName(): String {
        return "Android"
    }
}

actual fun getCurrentDateTimeInMilli(): Long {
    return System.currentTimeMillis()
}