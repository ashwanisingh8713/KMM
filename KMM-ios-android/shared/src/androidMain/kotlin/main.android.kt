import androidx.compose.runtime.Composable
import ui.vm.SectionListViewModel

actual fun getPlatformName(): String = "Android"

@Composable fun MainView(sectionListViewModel: SectionListViewModel) = App(sectionListViewModel)
