package com.ns.shopify.presentation.screen.more

import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.Modifier
import androidx.compose.material3.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import org.koin.core.component.KoinComponent

/**
 * Created by Ashwani Kumar Singh on 18,December,2023.
 */
class MoreScreen:Screen, KoinComponent {
    @Composable
    override fun Content() {
            Text(
                modifier = Modifier,
                text = "More Screen",
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Start,
            )
    }

}