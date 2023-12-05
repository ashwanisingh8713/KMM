package com.ns.shopify.presentation.componets

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.unit.dp

/**
 * Created by Ashwani Kumar Singh on 05,December,2023.
 */
@Composable
fun DefaultBackArrow(
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .size(30.dp)
            .clip(CircleShape)
            .clickable {
                onClick()
            },
        contentAlignment = Alignment.Center
    ) {

        val arrowBack = rememberVectorPainter(image = Icons.Outlined.ArrowBack)

        Image(
            painter = arrowBack,
            contentDescription = "Arrow Back",
            modifier = Modifier.size(20.dp)
        )
    }
}