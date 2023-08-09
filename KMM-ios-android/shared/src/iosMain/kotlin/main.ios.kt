import androidx.compose.ui.window.ComposeUIViewController
import platform.UIKit.UIViewController
import ui.getScreenWidth

fun MainViewController(): UIViewController = ComposeUIViewController {
    App(getScreenWidth())
}