package ui.screens.premium

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import cafe.adriel.voyager.core.screen.Screen
import domain.mapper.ArticleMapper
import domain.model.Article
import domain.model.PremiumContent
import domain.model.SectionContent
import ui.screens.home.pages.PostCard_New

/**
 * Created by Ashwani Kumar Singh on 31,July,2023.
 */

@Composable
fun PremiumContentUI(
    listState: LazyListState,
    sectionContent: PremiumContent?,
    onArticleClick: (article: ArticleMapper) -> Unit,
    isLoading: Boolean,
    error: String?,
) {
    if (isLoading) { // Loading Block

        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Loading...",
                style = MaterialTheme.typography.displayMedium,
                textAlign = TextAlign.Center
            )
        }
    } else if (error != null) { // Error Block
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = error,
                modifier = Modifier.padding(16.dp),
                style = MaterialTheme.typography.labelMedium,
                textAlign = TextAlign.Left,
            )
        }
    } else if (sectionContent != null) { // Data Block

        sectionContent?.let {
            // LazyColumn
            LazyColumn(
                state = listState,
                modifier = Modifier,
                contentPadding = PaddingValues(8.dp),
//                verticalArrangement = Arrangement.Top,
                verticalArrangement = Arrangement.spacedBy(12.dp)

            ) {

                items(items = sectionContent.data, key = { it.articleId }) { data ->
                    PremiumItemCard(
                        isLoading = isLoading,
                        data = data,
                        onArticleClick = onArticleClick
                    )
                    Divider(color = Color.Black, thickness = 1.dp)
                }
            }
        }
    } else {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "No Data",
                style = MaterialTheme.typography.displayMedium,
                textAlign = TextAlign.Center
            )
        }
    }
}
