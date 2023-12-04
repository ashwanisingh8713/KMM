package ui.screens.detail

import androidx.compose.runtime.Composable
import domain.mapper.ArticleMapper
import domain.model.Article

/**
 * Created by Ashwani Kumar Singh on 08,September,2023.
 */

expect fun onSharePressed(article: ArticleMapper)
expect fun onTextToSpeechPress(article: ArticleMapper)
expect fun onCommentPress(article: ArticleMapper)
expect fun onFontPress(descriptionFontSize: Int)