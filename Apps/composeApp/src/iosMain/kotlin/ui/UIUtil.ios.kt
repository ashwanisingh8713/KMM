package ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.interop.UIKitView
import kotlinx.cinterop.ExperimentalForeignApi
import platform.CoreGraphics.CGFloat
import platform.CoreGraphics.CGRect
import platform.Foundation.NSDate
import platform.Foundation.NSUUID
import platform.Foundation.timeIntervalSince1970
import platform.UIKit.UIScreen
import platform.UIKit.UIWebView

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


actual fun randomUUID(): String = NSUUID().UUIDString()

@OptIn(ExperimentalForeignApi::class)
@Composable
actual fun htmlDescription(description: String, modifier: androidx.compose.ui.Modifier) {
    val uu = remember { UIWebView() }
    UIKitView(
        modifier = modifier,
        factory = {
            uu
        },
        update = {
            it.loadHTMLString(
                "<html><body>$description</body></html>",
                baseURL = null
            )
        }
    )
}

// <html><body><h1>Hello, world!</h1></body></html>