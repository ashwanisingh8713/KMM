package util

import platform.Foundation.NSDate
import platform.Foundation.timeIntervalSince1970

/**
 * Created by Ashwani Kumar Singh on 20,September,2023.
 */

actual class PlatformUtil() {
    actual fun getPlatformName(): String {
        return "iOS"
    }
}

actual fun getCurrentDateTimeInMilli(): Long {
    val date = NSDate()
    val milliseconds = date.timeIntervalSince1970() * 1000
    return milliseconds.toLong()
}