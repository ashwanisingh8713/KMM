import android.content.Context
import android.content.pm.ApplicationInfo
import androidx.compose.runtime.Composable

import ui.getScreenWidth
//import ui.vm.SectionListViewModel



fun Context.isDebug() = 0 != applicationInfo.flags and ApplicationInfo.FLAG_DEBUGGABLE

@Composable fun MainView() = App(getScreenWidth())
