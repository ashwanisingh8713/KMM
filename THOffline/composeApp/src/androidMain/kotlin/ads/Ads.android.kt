package ads

/**
 * Created by Ashwani Kumar Singh on 07,September,2023.
 */

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.SaverScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView

class AdViewMapSaver(private val adViewMap: MutableMap<Int, AdView>) :
    Saver<Map<Int, AdView>, Map<Int, AdView>> {

    override fun SaverScope.save(value: Map<Int, AdView>): Map<Int, AdView> {
        return value
    }

    override fun restore(value: Map<Int, AdView>): Map<Int, AdView> {
//        adViewMap.clear()
        adViewMap.putAll(value)
        return adViewMap
    }
}


@Composable
actual fun AdmobBanner(modifier: Modifier) {
    val adViewMap = rememberSaveable { mutableMapOf<Int, AdView>() }
    val context = LocalContext.current
    val mapAd: AdView? = adViewMap[1]

    if (mapAd != null) {
        AndroidView(
            modifier = Modifier.fillMaxWidth(),
            factory = { context ->
                mapAd
            }
        )
        return
    } else {
        val ad = AdView(context).apply {
            // on below line specifying ad size
            //adSize = AdSize.BANNER
            // on below line specifying ad unit id
            // currently added a test ad unit id.
            setAdSize(AdSize.MEDIUM_RECTANGLE)
            adUnitId = "ca-app-pub-3940256099942544/9214589741"
            // calling load ad to load our ad.
            loadAd(AdRequest.Builder().build())
        }
        rememberSaveable(saver = AdViewMapSaver(adViewMap)) {
            val mm = mutableMapOf<Int, AdView>()
            mm.put(1, ad)
            mm
        }


        AndroidView(
            modifier = Modifier.fillMaxWidth(),
            factory = { context ->
                // on below line specifying ad view.
                ad
            }
        )
    }
}
