package util

/**
 * Created by Ashwani Kumar Singh on 20,September,2023.
 */

expect class PlatformUtil {
    fun getPlatformName(): String
}

expect fun getCurrentDateTimeInMilli(): Long