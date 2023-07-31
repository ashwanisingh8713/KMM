package ui

import kotlinx.cinterop.ExperimentalForeignApi
import platform.CoreGraphics.CGFloat
import platform.CoreGraphics.CGRect
import platform.Foundation.NSDate
import platform.Foundation.timeIntervalSince1970
import platform.UIKit.UIScreen

/**
 * Created by Ashwani Kumar Singh on 31,July,2023.
 */

actual fun timestampMs(): Long {
    return (NSDate().timeIntervalSince1970 * 1000).toLong()
}

actual fun getScreenWidth(): Int {
    return 360
}
actual fun getPlatform(): String {

    return "iOS"
}
actual fun getModelName(): String {

    return "iPhone 16"
}
actual fun getOSVersion(): String {

    return "iOS 16"
}