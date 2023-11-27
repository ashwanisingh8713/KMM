import androidx.compose.ui.window.ComposeUIViewController
import com.ns.shopify.kmm.App
import platform.UIKit.UIViewController

fun MainViewController(): UIViewController {
    return ComposeUIViewController { App() }
}
