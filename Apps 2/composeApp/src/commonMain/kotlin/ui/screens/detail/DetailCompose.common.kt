package ui.screens.detail

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import domain.mapper.ArticleMapper

/**
 * Created by Ashwani Kumar Singh on 15,September,2023.
 */

@Composable
expect fun DetailPageCompose(article: ArticleMapper, modifier: Modifier, onWebPageTouch:()->Unit)

@Composable
expect fun HtmlDescription(description: String, modifier: Modifier, onWebPageTouch:()->Unit)

@Composable
expect fun LoadTaboolaWidget(pageUrl: String, modifier: Modifier)