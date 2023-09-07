package data.model

import domain.model.Article
import domain.model.Widget

/**
 * Created by Ashwani Kumar Singh on 06,September,2023.
 */
data class SectionContentListData(val secId: Int, val viewType: String, val article: Article?= null, var widget: Widget? = null) {

    override fun equals(other: Any?): Boolean {
        if(other !is SectionContentListData) {
            return false
        }
        return this.secId == other.secId && this.viewType == other.viewType
    }

    override fun hashCode(): Int {
        return secId.hashCode() + viewType.hashCode()
    }

}