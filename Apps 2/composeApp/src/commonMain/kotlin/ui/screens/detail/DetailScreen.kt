package ui.screens.detail

import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.core.screen.ScreenKey
import cafe.adriel.voyager.core.screen.uniqueScreenKey
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import domain.model.Article

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
            BoxWithConstraints(
                Modifier.padding(it),
                contentAlignment = Alignment.TopCenter
            ){
                DetailPageCompose(article, Modifier.fillMaxSize())
            }


        }
    )

}


