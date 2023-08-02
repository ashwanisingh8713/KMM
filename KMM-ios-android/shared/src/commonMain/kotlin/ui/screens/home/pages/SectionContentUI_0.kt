package ui.screens.home.pages

import androidx.compose.ui.unit.dp
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import domain.model.SectionContent

/**
 * Created by Ashwani Kumar Singh on 31,July,2023.
 */

@Composable
fun SectionContentUI_0(sectionContent: SectionContent?, isLoading: Boolean, error: String?, secId: Int, secName: String, type: String) {
    if (isLoading) {
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
    } else {
        if (sectionContent != null) {
            val listState = rememberLazyListState()
            sectionContent?.let {
                // LazyColumn
                LazyColumn(
                    state = listState,
                    modifier = Modifier,
                    contentPadding = PaddingValues(0.dp),
                ) {
                    items(items = sectionContent.data.article, key = {it.aid}) { article ->
                        PostCard(isLoading = isLoading, article = article)
                    }
                }
            }
        } else if(error != null && error!!.isNotEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = error ?: "Something went wrong",
                    style = MaterialTheme.typography.displayMedium,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}