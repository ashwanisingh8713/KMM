import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import data.datasource.ApiRequest
import data.repository.SectionsListRepoImpl
import domain.repository.SectionsListRepo
import domain.usecase.SectionListUseCase
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import theme.Material3ComposeTheme
import ui.pager.HorizPager
import ui.vm.SectionListViewModel

@OptIn(ExperimentalResourceApi::class)
@Composable
fun App() {

    LaunchedEffect(true) {
        val apiRequest = ApiRequest()
        val sectionsListRepoImpl = SectionsListRepoImpl(apiRequest)
        val sectionListUseCase = SectionListUseCase(sectionsListRepoImpl)
        val sectionListViewModel = SectionListViewModel(sectionListUseCase)

        sectionListViewModel.makeSectionApiRequest()
    }

    Material3ComposeTheme {
        var greetingText by remember { mutableStateOf("Hello, World!") }
        var showImage by remember { mutableStateOf(false) }
        Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
            Button(onClick = {
                greetingText = "Click Me, ${getPlatformName()}"
                showImage = !showImage
            }) {
                Text(greetingText)
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



expect fun getPlatformName(): String