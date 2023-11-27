import androidx.compose.ui.window.ComposeUIViewController
import com.daniel_avila.App
import platform.UIKit.UIViewController

fun MainViewController(): UIViewController {
    return ComposeUIViewController { App() }
}
