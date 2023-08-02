package ui.model

import androidx.compose.runtime.Composable

/**
 * Created by Ashwani Kumar Singh on 31,July,2023.
 */
data class SectionTabItem(
    val tabId: String,
    val secName: String,
    val isSelected: Boolean = false,
    val secId: Int,
    val secType: String,

)
