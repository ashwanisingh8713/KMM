
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import org.jetbrains.compose.resources.ExperimentalResourceApi
import ui.theme.AppTheme
import ui.screens.home.HomeBottomBar
import ui.screens.home.HomeContent
import ui.screens.home.HomeTopBar
import ui.vm.SectionListViewModel

@OptIn(ExperimentalResourceApi::class)
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


