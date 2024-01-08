import androidx.compose.ui.uikit.OnFocusBehavior
import androidx.compose.ui.window.ComposeUIViewController
import androidx.compose.ui.window.Window
import com.app.App
import platform.UIKit.UIScreen
import platform.UIKit.UIUserInterfaceStyle

/*
fun MainViewController(): UIViewController {
    return ComposeUIViewController { App() }
}
*/

fun MainViewController() = ComposeUIViewController(
    configure = {
        onFocusBehavior = OnFocusBehavior.DoNothing
    }
) {
    val isDark =
        UIScreen.mainScreen.traitCollection.userInterfaceStyle == UIUserInterfaceStyle.UIUserInterfaceStyleDark

    App(
        isDark = isDark
    )
}


