import androidx.compose.runtime.Composable
import ui.getScreenWidth
import ui.vm.SectionListViewModel


@Composable fun MainView(sectionListViewModel: SectionListViewModel) = App(sectionListViewModel, getScreenWidth())
