package com.ns.shopify.presentation.componets

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Close
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * Created by Ashwani Kumar Singh on 05,December,2023.
 */
@Composable
fun ErrorSuggestion(message: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(
            space = 10.dp,
        ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(painter = rememberVectorPainter(image = Icons.Outlined.Close), contentDescription = "Error Icon")
        Text(text = message, color = MaterialTheme.colors.onPrimary, fontSize = 14.sp)
    }
}
