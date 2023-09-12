package ui.screens.detail

import androidx.compose.runtime.Composable
import domain.model.Article

/**
 * Created by Ashwani Kumar Singh on 08,September,2023.
 */

expect fun onSharePressed(article: Article)
expect fun onTextToSpeechPress(article: Article)
expect fun onCommentPress(article: Article)
expect fun onFontPress(descriptionFontSize: Int)