package ui.screens.detail

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Scaffold
import androidx.compose.material3.IconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material.icons.Icons
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.Comment
import androidx.compose.material.icons.filled.FormatSize
import androidx.compose.material.icons.filled.LogoDev
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.SurroundSound
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.core.screen.ScreenKey
import cafe.adriel.voyager.core.screen.uniqueScreenKey
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import domain.model.Article
import taboola.loadTaboolaWidget
import ui.screens.util.htmlDescription

/**
 * Created by Ashwani Kumar Singh on 03,August,2023.
 */

data class DetailScreen(private val article: Article) : Screen {

    override val key: ScreenKey = uniqueScreenKey

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow

        val isBookmarked by remember { mutableStateOf<Boolean>(false) }
        val isTextToSpeechEnabled by remember { mutableStateOf<Boolean>(false) }
        val descriptionFontSize by remember { mutableStateOf<Int>(16) }

        val onBackPress: () -> Unit = {
            navigator.pop()
        }
        val onSharePress: () -> Unit = {

        }
        val onTextToSpeechPress: () -> Unit = {
            onTextToSpeechPress(article)
        }
        val onCommentPress: () -> Unit = {

        }

        val onBookmarkPress: () -> Unit = {

        }

        val onFontPress: () -> Unit = {

        }

        ArticleDetailContents(
            article = article,
            onBackPress = onBackPress,
            onSharePress = onSharePress,
            onCommentPress = onCommentPress,
            onBookmarkPress = onBookmarkPress,
            onFontPress = onFontPress,
            onTextToSpeechPress = onTextToSpeechPress,
            isBookmarked = isBookmarked,
            isTextToSpeechEnabled = isTextToSpeechEnabled,
            descriptionFontSize = descriptionFontSize
        )
    }
}


@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun ArticleDetailContents(
    article: Article, onBackPress: () -> Unit, onSharePress: () -> Unit,
    onCommentPress: () -> Unit, onBookmarkPress: () -> Unit,
    onFontPress: () -> Unit, onTextToSpeechPress: () -> Unit,
    isBookmarked: Boolean, isTextToSpeechEnabled: Boolean, descriptionFontSize: Int
) {


    Scaffold(
        topBar = {
            ArticleDetailTopBar(
                onBackPress, onSharePress, onCommentPress, onBookmarkPress,
                onFontPress, onTextToSpeechPress, isBookmarked, isTextToSpeechEnabled
            )
        },
        bottomBar = {

        },
        content = {
                LazyColumn(modifier = Modifier.fillMaxSize()) {

                    item {
                        Column {
                            htmlDescription(article.de, modifier = Modifier)
                        // Showing Taboola Widgets
                        loadTaboolaWidget(pageUrl = article.al, modifier = Modifier.fillMaxWidth().fillMaxHeight(.1f))
                        }
                    }
                }

        }
    )

}


