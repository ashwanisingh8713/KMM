package ui.screens.premium

import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.core.screen.uniqueScreenKey
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import domain.model.Article
import ext.getScreenModel
import ui.screens.detail.DetailScreen
import ui.screens.home.pages.SectionContentUI_0
import ui.screens.util.ComposeTag
import ui.vm.PremiumContentViewModel
import ui.vm.SectionListViewModel

/**
 * Created by Ashwani Kumar Singh on 16,August,2023.
 */
class PremiumScreen : Screen {
    override val key = uniqueScreenKey
    @Composable
    override fun Content() {
        val viewModel = getScreenModel<PremiumContentViewModel>()

        val sectionContent by viewModel.successPremiumArticle.collectAsState()
        val isLoading by viewModel.premiumArticleLoading.collectAsState()
        val error by viewModel.premiumArticleError.collectAsState()

        LaunchedEffect(true) {
            viewModel.makePremiumContentApiRequest()
        }

        val navigator = LocalNavigator.currentOrThrow

        val onArticleClick: (Article) -> Unit = { article ->
            navigator.push(DetailScreen(article))
        }

        PremiumContentUI(
            listState = rememberLazyListState(),
            sectionContent = sectionContent,
            isLoading = isLoading,
            error = error,
            onArticleClick = onArticleClick
        )

    }


}