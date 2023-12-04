package com.app

import android.util.Log
import com.app.util.TAG


/**
 * Created by Ashwani Kumar Singh on 04,December,2023.
 */

actual fun printLog(msg: String) {
    Log.d(TAG, "printLog: $msg")
}