package ui.screens.detail

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Scaffold
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.core.screen.ScreenKey
import cafe.adriel.voyager.core.screen.uniqueScreenKey
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import domain.mapper.ArticleMapper
import domain.model.Article

/**
 * Created by Ashwani Kumar Singh on 03,August,2023.
 */

data class DetailScreen(private val article: ArticleMapper, private val allArticles: List<ArticleMapper> = emptyList()) : Screen {

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
            descriptionFontSize = descriptionFontSize,
            allArticles= allArticles
        )
    }
}


@Composable
private fun ArticleDetailContents(
    article: ArticleMapper, onBackPress: () -> Unit, onSharePress: () -> Unit,
    onCommentPress: () -> Unit, onBookmarkPress: () -> Unit,
    onFontPress: () -> Unit, onTextToSpeechPress: () -> Unit,
    isBookmarked: Boolean, isTextToSpeechEnabled: Boolean, descriptionFontSize: Int,
    allArticles: List<ArticleMapper>
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
            ) {
                if(allArticles.isEmpty()) {
                    DetailPageCompose(article, Modifier.fillMaxSize())
                } else {
                    var initialPage = allArticles.indexOf(article)
                    if(initialPage == -1) {
                        initialPage = 0
                    }
                    DetailPager(allArticles, initialPage = initialPage)
                }
            }


        }
    )

}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun DetailPager(allArticles: List<ArticleMapper>, initialPage: Int = 0) {
    val pagerState = rememberPagerState(initialPage = initialPage, initialPageOffsetFraction = 0.0f, pageCount = {
        allArticles.size
    })

    HorizontalPager(modifier = Modifier.fillMaxSize(), state = pagerState,
        beyondBoundsPageCount = 1,
        pageSpacing = 0.dp,
        contentPadding = PaddingValues(0.dp)
    ) { page ->
        // Our page content
        val article = allArticles[page]
        DetailPageCompose(article, Modifier.fillMaxWidth())
    }


}



