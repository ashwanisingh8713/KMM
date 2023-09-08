package taboola

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

/**
 * Created by Ashwani Kumar Singh on 07,September,2023.
 */

@Composable
expect fun loadTaboolaWidget(pageUrl: String, modifier: Modifier)