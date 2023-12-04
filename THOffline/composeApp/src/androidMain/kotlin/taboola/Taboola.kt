package taboola

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import com.taboola.android.TBLClassicUnit
import com.taboola.android.Taboola
import com.taboola.android.annotations.TBL_PLACEMENT_TYPE
import com.taboola.android.listeners.TBLClassicListener
import util.getArticleIdFromArticleUrl

/**
 * Created by Ashwani Kumar Singh on 07,September,2023.
 */






@Composable
fun LoadSingleTaboola(pageUrl: String) {
    val context = LocalContext.current
    val tblClassicUnit: TBLClassicUnit = createTaboolaWidget(context, pageUrl)
    tblClassicUnit.interceptScroll = true

    // Fetch content for Unit
    tblClassicUnit.fetchContent()
    AndroidView(factory = {
        tblClassicUnit
    })
}


/**
 * Define a Page that represents this screen, get a Unit from it, add it to screen and fetch its content
 * Notice: A Unit of unlimited items, called "Feed" in Taboola, can be set in TBL_PLACEMENT_TYPE.PAGE_BOTTOM only.
 */
fun createTaboolaWidget(context: Context?, pageUrl: String): TBLClassicUnit {

    //Create TBLClassicPage
//    val tblClassicPage = Taboola.getClassicPage("https://blog.taboola.com", "article")
    val tblClassicPage = Taboola.getClassicPage(pageUrl, "article")

    //Choose widget properties (placementName, Mode, pageUrl..etc)
    val properties: PlacementInfo.THProperties = PlacementInfo.THProperties(pageLink = pageUrl)

    //Define a single Unit to display
    val tblClassicUnit: TBLClassicUnit = tblClassicPage.build(context,
        properties.placementName ,
        properties.mode,
        TBL_PLACEMENT_TYPE.PAGE_BOTTOM,
        object: TBLClassicListener(){
        override fun onAdReceiveSuccess() {
            super.onAdReceiveSuccess()
            println("Taboola | onAdReceiveSuccess")
        }

        override fun onAdReceiveFail(error: String?) {
            super.onAdReceiveFail(error)
            println("Taboola | onAdReceiveFail: $error")
        }

            override fun onItemClick(
                placementName: String?,
                itemId: String?,
                clickUrl: String,
                isOrganic: Boolean,
                customData: String?
            ): Boolean {
                //return super.onItemClick(placementName, itemId, clickUrl, isOrganic, customData)
                if(isOrganic) {
                    val articleId: Int = getArticleIdFromArticleUrl(clickUrl)
                    return false
                } else {
                    return true
                }
            }

            override fun onEvent(actionType: Int, data: String?) {
                super.onEvent(actionType, data)
            }
    })
    return tblClassicUnit
}


