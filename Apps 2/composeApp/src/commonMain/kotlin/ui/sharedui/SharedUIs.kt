package ui.sharedui

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

/**
 * Created by Ashwani Kumar Singh on 20,September,2023.
 */

@Composable
fun <T> LoadingView(
    modifier: Modifier = Modifier,
    loading: Boolean,
    content: @Composable () -> T
) {
    Box(modifier = modifier) {
        content()
        if (loading) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center),
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}

