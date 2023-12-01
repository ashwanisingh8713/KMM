import androidx.compose.ui.window.ComposeUIViewController
import com.app.App
import platform.UIKit.UIViewController

fun MainViewController(): UIViewController {
    return ComposeUIViewController { App() }
}
