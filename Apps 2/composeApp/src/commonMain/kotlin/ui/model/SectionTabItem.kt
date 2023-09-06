package ui.model

import domain.model.Widget

/**
 * Created by Ashwani Kumar Singh on 31,July,2023.
 */
data class SectionTabItem(
    val tabId: String,
    var secName: String,
    val isSelected: Boolean = false,
    var secId: Int,
    var secType: String,
    val widget: List<Widget> = emptyList()
    )
