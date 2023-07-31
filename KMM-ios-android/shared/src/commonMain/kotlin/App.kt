import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import ui.getPlatform
import ui.theme.AppTheme
import ui.pager.HorizPager
import ui.theme.Theme
import ui.vm.SectionListViewModel

@OptIn(ExperimentalResourceApi::class)
@Composable
fun App(sectionListViewModel: SectionListViewModel, screenWidthDp:Int) {
    LaunchedEffect(true) {
        sectionListViewModel.makeSectionApiRequest()
    }

    AppTheme(screenWidthDp = screenWidthDp) {
        var greetingText by remember { mutableStateOf("Hello, World!") }
        var showImage by remember { mutableStateOf(false) }
        Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
            Button(onClick = {
                greetingText = "Click Me, ${getPlatform()}"
                showImage = !showImage
            }) {
                Text(greetingText, modifier = Modifier.padding(Theme.dimens.grid_4))
            }
            AnimatedVisibility(showImage) {
                Image(
                    painterResource("compose-multiplatform.xml"),
                    null
                )
            }
            HorizPager()

        }
    }
}


