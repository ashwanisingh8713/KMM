package ui.navigation

import androidx.compose.runtime.Immutable

/**
 * Created by Ashwani Kumar Singh on 31,July,2023.
 */
@Immutable
sealed interface Screen {
    /**
     * Home screen i.e. Navigation Bar, Tab Bar, Listing page
     */
    @Immutable
    object Home : Screen {
        override fun equals(other: Any?): Boolean {
            return other === Home
        }

        override fun hashCode(): Int {
            return this::class.simpleName.hashCode()
        }
    }

    /**
     * Details screen
     */
    @Immutable
    data class ArticleDetail(val postId: Int) : Screen
}