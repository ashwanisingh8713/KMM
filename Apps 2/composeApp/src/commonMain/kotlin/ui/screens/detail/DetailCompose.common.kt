package ui.screens.detail

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import domain.model.Article

/**
 * Created by Ashwani Kumar Singh on 15,September,2023.
 */

@Composable
expect fun DetailPageCompose(article: Article, modifier: Modifier)

@Composable
expect fun HtmlDescription(description: String, modifier: Modifier)

@Composable
expect fun LoadTaboolaWidget(pageUrl: String, modifier: Modifier)