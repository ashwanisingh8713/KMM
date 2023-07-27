import androidx.compose.ui.window.ComposeUIViewController
import platform.UIKit.UIViewController
import ui.vm.SectionListViewModel

actual fun getPlatformName(): String = "iOS"

fun MainViewController(sectionListViewModel: SectionListViewModel): UIViewController = ComposeUIViewController {
    App(sectionListViewModel)
}