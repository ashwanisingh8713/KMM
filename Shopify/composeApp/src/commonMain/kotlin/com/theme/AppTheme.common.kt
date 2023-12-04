package com.theme

import androidx.compose.runtime.Composable


/**
 * Created by Ashwani Kumar Singh on 04,December,2023.
 */
@Composable
expect fun AppTheme(
    isDark: Boolean,
    content: @Composable () -> Unit
)