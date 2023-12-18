package com.ns.shopify.presentation.screen.product_detail

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 * Created by Ashwani Kumar Singh on 18,December,2023.
 */


@Composable
actual fun ProductDetailComponent(htmlDescription: String) {
    Text(text = htmlDescription)
    Spacer(modifier = Modifier.height(50.dp))
}
