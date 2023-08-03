
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.navigator.Navigator
import ui.theme.AppTheme
import ui.screens.home.HomeBottomBar
import ui.screens.home.HomeNavAndTabs
import ui.screens.home.HomeScreen
import ui.screens.home.HomeTopBar
import ui.vm.SectionListViewModel

@Composable
fun App(viewModel: SectionListViewModel, screenWidthDp: Int) {
    AppTheme(screenWidthDp = screenWidthDp) {
    Navigator(HomeScreen(viewModel))
    }
}




