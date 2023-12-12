package com.ns.shopify.presentation.screen.home

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Home
import androidx.compose.ui.graphics.vector.ImageVector

sealed class TopBarItem(
    val icon: ImageVector,
    val label: String
)  {
    object General: TopBarItem(
        icon = Icons.Default.Home,
        label = "General"
    )
    object Business: TopBarItem(
        icon = Icons.Default.ArrowForward,
        label = "Business"
    )
    object Traveling: TopBarItem(
        icon = Icons.Default.Call,
        label = "Technology"
    )
}