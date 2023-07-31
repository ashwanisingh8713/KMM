import androidx.compose.ui.window.ComposeUIViewController
import platform.UIKit.UIViewController
import ui.getScreenWidth
import ui.vm.SectionListViewModel

fun MainViewController(sectionListViewModel: SectionListViewModel): UIViewController = ComposeUIViewController {
    App(sectionListViewModel, getScreenWidth())
}