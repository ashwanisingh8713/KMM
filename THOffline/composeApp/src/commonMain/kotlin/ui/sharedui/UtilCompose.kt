package ui.sharedui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource

/**
 * Created by Ashwani Kumar Singh on 08,August,2023.
 */

@OptIn(ExperimentalResourceApi::class)
@Composable
fun NoNetworkUI(msg: String) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column {
            Image(
                painter = painterResource("no_network.png"),
                contentDescription = null,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
            Text(
                text = if (msg.contains("Unable to resolve host")) "No Internet Connection" else msg,
                style = MaterialTheme.typography.headlineMedium,
                textAlign = TextAlign.Center
            )
        }

    }
}

val ComposeTag = "AshwaniCompose"

