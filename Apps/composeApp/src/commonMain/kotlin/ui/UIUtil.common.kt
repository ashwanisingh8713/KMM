package ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

/**
 * Created by Ashwani Kumar Singh on 31,July,2023.
 */


fun timeToString(timestampMs: Long): String {
    val seconds = timestampMs
    val minutes = seconds / 1000 / 60
    val hours = minutes / 24

    val m = minutes % 60
    val h = hours % 24

    val mm = if (m < 10) {
        "0$m"
    } else {
        m.toString()
    }
    val hh = if (h < 10) {
        "0$h"
    } else {
        h.toString()
    }
    return "$hh:$mm"
}
expect fun timestampMs(): Long

expect fun getScreenWidth(): Int
expect fun getPlatform(): String
expect fun getModelName(): String
expect fun getOSVersion(): String

// Common
expect fun randomUUID(): String

@Composable
expect fun htmlDescription(description: String, modifier: Modifier)