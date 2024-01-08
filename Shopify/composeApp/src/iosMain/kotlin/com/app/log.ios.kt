package com.app

import com.app.util.TAG
import platform.Foundation.NSLog


/**
 * Created by Ashwani Kumar Singh on 04,December,2023.
 */

actual fun printLog(msg: String) {
    NSLog("$TAG $msg")
}