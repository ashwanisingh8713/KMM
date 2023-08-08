
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.navigator.Navigator
import ui.model.SectionTabItem
import ui.theme.AppTheme
import ui.screens.home.HomeBottomBar
import ui.screens.home.HomeNavAndTabs
import ui.screens.home.HomeScreen
import ui.screens.home.HomeTopBar
import ui.vm.SectionListViewModel

@Composable
fun App(viewModel: SectionListViewModel, screenWidthDp: Int) {
    var tabRowItems by remember { mutableStateOf(listOf<SectionTabItem>()) }
    var sectionListLoading by remember { mutableStateOf(true) }
    var sectionListError by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(true) {
        viewModel.makeSectionListApiRequest()
    }

    viewModel.successSectionList.collectAsState().value?.let { it ->
        val secList = it.data.section
        tabRowItems = secList.filter { it.type != "static" }.map {
            SectionTabItem(
                tabId = it.secId.toString(),
                secName = it.secName,
                isSelected = false,
                secId = it.secId,
                secType = it.type,
            )
        }
    }

    viewModel.sectionListLoading.collectAsState().value?.let { it ->
        sectionListLoading = it
    }

    viewModel.sectionListError.collectAsState().value?.let { it ->
        sectionListError = it
    }

    AppTheme(screenWidthDp = screenWidthDp) {
    Navigator(HomeScreen(viewModel, tabRowItems = tabRowItems, sectionListLoading = sectionListLoading, sectionListError= sectionListError))
    }
}




