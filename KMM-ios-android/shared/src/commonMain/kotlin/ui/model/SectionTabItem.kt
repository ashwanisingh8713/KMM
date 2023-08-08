package ui.model

import androidx.compose.runtime.Composable

/**
 * Created by Ashwani Kumar Singh on 31,July,2023.
 */
data class SectionTabItem(
    val tabId: String,
    var secName: String,
    val isSelected: Boolean = false,
    var secId: Int,
    var secType: String,

    )
