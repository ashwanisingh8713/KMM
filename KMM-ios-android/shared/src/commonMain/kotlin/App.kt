
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import ui.dimens.LocalAppDimens
import ui.dimens.smallDimensions
import ui.theme.AppTheme
import ui.screens.home.HomeBottomBar
import ui.screens.home.HomeContent
import ui.screens.home.HomeTopBar
import ui.screens.home.SectionContentUI
import ui.vm.SectionListViewModel

@Composable
fun App(viewModel: SectionListViewModel, screenWidthDp: Int) {
    AppTheme(screenWidthDp = screenWidthDp) {
        Scaffold(

            bottomBar = {
                HomeBottomBar()
            },
            topBar = {
                HomeTopBar()

            },

        ) {
            BoxWithConstraints(
                Modifier.padding(it),
                contentAlignment = Alignment.TopCenter
            ) {
                HomeContent(viewModel)
            }
        }




    }
}


