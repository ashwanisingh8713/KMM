package ui.screens.detail

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.core.screen.ScreenKey
import cafe.adriel.voyager.core.screen.uniqueScreenKey
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import domain.mapper.ArticleMapper
import ext.getScreenModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import ui.composables.Stepper_n
import ui.viewModel.ArticleIdDetailViewModel

/**
 * Created by Ashwani Kumar Singh on 03,August,2023.
 */

data class DetailScreen(private val article: ArticleMapper, private val allArticles: List<ArticleMapper> = emptyList()) : Screen {

    override val key: ScreenKey = uniqueScreenKey

    @Composable
    override fun Content() {

        val articleIdDetailViewModel = getScreenModel<ArticleIdDetailViewModel>()

        var fontSizeStepperVisible by remember { mutableStateOf(false)}
        var fontSizeForWebPage by remember { mutableStateOf(18)}

        val navigator = LocalNavigator.currentOrThrow

        val isBookmarked by remember { mutableStateOf<Boolean>(false) }
        val isTextToSpeechEnabled by remember { mutableStateOf<Boolean>(false) }
        val descriptionFontSize by remember { mutableStateOf<Int>(16) }

        val pointerModifier = Modifier
            .pointerInput(Unit) {
                /*detectDragGestures { change, dragAmount ->
                    println("AshwaniDetail :: detectDragGestures")
                }*/
                /*detectHorizontalDragGestures { change, dragAmount ->
                    println("AshwaniDetail :: detectHorizontalDragGestures")
                }
                detectTapGestures {
                    println("AshwaniDetail :: detectTapGestures")
                }*/

                awaitEachGesture {
                        awaitFirstDown()
                        // ACTION_DOWN here
                        fontSizeStepperVisible = false
                        println("AshwaniDetail :: ACTION_DOWN" )

                        /*do {

                            //This PointerEvent contains details including
                            // event, id, position and more
                            val event: PointerEvent = awaitPointerEvent()
                            // ACTION_MOVE loop

                            println("AshwaniDetail :: ACTION_MOVE = $event" )

                            // Consuming event prevents other gestures or scroll to intercept
                            event.changes.forEach { pointerInputChange: PointerInputChange ->
                                pointerInputChange.consumePositionChange()
                            }
                        } while (event.changes.any { it.pressed })*/

                        // ACTION_UP is here
                        println("AshwaniDetail :: ACTION_UP" )
                    }

            }

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
            fontSizeStepperVisible = !fontSizeStepperVisible
        }

        val fontSizeChanged:(Int)->Unit = {
            println("AshwaniFont FontSizeForWebPage Rule-2 = $fontSizeForWebPage")
            fontSizeForWebPage = it
            println("AshwaniFont FontSizeForWebPage Rule-3 = $fontSizeForWebPage")
        }

        val onPageChanged:(Int)->Unit = {
            fontSizeStepperVisible = false
        }
        val onWebPageTouch:()->Unit = {
            if(fontSizeStepperVisible) {
                fontSizeStepperVisible = false
            }
        }


        // On ArticleLink Click, Redirection to detail page
        LaunchedEffect(true) {
            articleIdDetailViewModel.articleIdDetailSuccess.collectLatest {
                navigator.push(DetailScreen(article = it!!))
            }
        }

        val onLinkClick:(String)->Unit= {
            val subArr = it.split("/")
            val lastSubStr = subArr[subArr.size-1]
            val artileId = lastSubStr.filter { it.isDigit() }.toInt()
            articleIdDetailViewModel.makArticleIdApiRequest(artileId)
        }

        Box {
            ArticleDetailContents(
                modifier = pointerModifier,
                article = article,
                onBackPress = onBackPress,
                onSharePress = onSharePress,
                onCommentPress = onCommentPress,
                onBookmarkPress = onBookmarkPress,
                onFontPress = onFontPress,
                onTextToSpeechPress = onTextToSpeechPress,
                onPageChanged = onPageChanged,
                isBookmarked = isBookmarked,
                isTextToSpeechEnabled = isTextToSpeechEnabled,
                descriptionFontSize = descriptionFontSize,
                allArticles = allArticles,
                onWebPageTouch = onWebPageTouch,
                fontSizeForWebPage = fontSizeForWebPage,
                onLinkClick = onLinkClick
            )

            FontSizeChangeCompose(fontSizeStepperVisible, fontSizeChanged)

        }
    }
}


@Composable
private fun ArticleDetailContents(
    modifier: Modifier,
    article: ArticleMapper, onBackPress: () -> Unit, onSharePress: () -> Unit,
    onCommentPress: () -> Unit, onBookmarkPress: () -> Unit,
    onFontPress: () -> Unit, onTextToSpeechPress: () -> Unit, onPageChanged:(Int)->Unit,
    isBookmarked: Boolean, isTextToSpeechEnabled: Boolean, descriptionFontSize: Int, fontSizeForWebPage: Int,
    allArticles: List<ArticleMapper>, onWebPageTouch:()->Unit,onLinkClick:(String)->Unit
) {

    Scaffold(
        modifier = modifier,
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
                modifier = Modifier.padding(it),
                contentAlignment = Alignment.TopCenter
            ) {
                if(allArticles.isEmpty()) {
                    DetailPageCompose(article, Modifier.fillMaxSize(), onWebPageTouch = onWebPageTouch, fontSizeForWebPage = fontSizeForWebPage, onLinkClick=onLinkClick)
                } else {
                    var initialPage = allArticles.indexOf(article)
                    if(initialPage == -1) {
                        initialPage = 0
                    }
                    DetailPager(modifier, allArticles, initialPage = initialPage, onPageChanged = onPageChanged, onWebPageTouch=onWebPageTouch, fontSizeForWebPage = fontSizeForWebPage, onLinkClick=onLinkClick)
                }
            }


        }
    )

}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun DetailPager(modifier: Modifier, allArticles: List<ArticleMapper>, initialPage: Int = 0, onPageChanged:(Int)->Unit, onWebPageTouch:()->Unit, fontSizeForWebPage: Int, onLinkClick:(String)->Unit) {
    val pagerState = rememberPagerState(initialPage = initialPage, initialPageOffsetFraction = 0.0f, pageCount = {
        allArticles.size
    })

    HorizontalPager(modifier = modifier, state = pagerState,
        beyondBoundsPageCount = 1,
        pageSpacing = 0.dp,
        contentPadding = PaddingValues(0.dp)
    ) { page ->
        // Our page content
        val article = allArticles[page]
        DetailPageCompose(article, modifier, onWebPageTouch = onWebPageTouch, fontSizeForWebPage = fontSizeForWebPage, onLinkClick)
        LaunchedEffect(pagerState) {
            snapshotFlow {
                pagerState.currentPage
            }.distinctUntilChanged().collect { page ->
                // Now you can use selected page
                onPageChanged(page)
            }
        }

    }


}

@Composable
fun FontSizeChangeCompose(fontSizeStepperVisible: Boolean, fontSizeChanged:(Int)->Unit) {
    var currentStep by rememberSaveable { mutableStateOf(2) }
    if (fontSizeStepperVisible) {
        val step1Click: (Int) -> Unit = {
            fontSizeChanged(it)
            currentStep = 1
        }

        val step2Click: (Int) -> Unit = {
            fontSizeChanged(it)
            currentStep = 2
        }

        val step3Click: (Int) -> Unit = {
            fontSizeChanged(it)
            currentStep = 3
        }

        val step4Click: (Int) -> Unit = {
            fontSizeChanged(it)
            currentStep = 4
        }

        val titleList = arrayListOf("XS", "S", "L", "XL")
        val valueList = arrayListOf(20, 26, 32, 38)
        val numberStep = 4

        Stepper_n(
            numberOfSteps = numberStep,
            currentStep = currentStep,
            stepDescriptionList = titleList,
            stepValueList = valueList,
            step1Click = step1Click,
            step2Click = step2Click,
            step3Click = step3Click,
            step4Click = step4Click
//            unSelectedColor= Color.LightGray,
//            selectedColor = Color.Magenta,
//            isRainbow = true
        )
    }
}



