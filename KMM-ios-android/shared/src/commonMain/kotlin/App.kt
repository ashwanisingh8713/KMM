
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import cafe.adriel.voyager.navigator.Navigator
import ui.theme.AppTheme
import ui.screens.home.HomeScreen
import ui.vm.SectionListViewModel

@Composable
fun App(viewModel: SectionListViewModel, screenWidthDp: Int) {

    LaunchedEffect(true) {
        viewModel.makeSectionListApiRequest()
    }





    AppTheme(screenWidthDp = screenWidthDp) {
    Navigator(HomeScreen(viewModel))
    }
}




