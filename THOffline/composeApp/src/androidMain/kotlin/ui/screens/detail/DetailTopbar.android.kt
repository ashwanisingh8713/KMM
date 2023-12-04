package ui.screens.detail

import android.content.Context
import android.speech.tts.TextToSpeech
import com.compose.AndroidApp
import domain.mapper.ArticleMapper
import domain.model.Article
import ui.screens.util.HtmlTextUtil
import java.util.Locale

/**
 * Created by Ashwani Kumar Singh on 08,September,2023.
 */

actual fun onSharePressed(article: ArticleMapper) {

}


actual fun onTextToSpeechPress(article: ArticleMapper) {
    textToSpeech(AndroidApp.appContext, article.de!!, article.aid.toInt())
}
actual fun onCommentPress(article: ArticleMapper) {

}
actual fun onFontPress(descriptionFontSize: Int) {

}

private  var  textToSpeech:TextToSpeech? = null
fun textToSpeech(context: Context, text: String, articleId: Int) {

    val speakableText = HtmlTextUtil.speakableText(
        text,
        "",
        ""
    )
    val parts = HtmlTextUtil.splitOnPunctuation(speakableText)
    var count = 0
    val utterenceId: String = utterenceId(articleId, count)

    val myHashRender: HashMap<String, String> = HashMap<String, String>()
    myHashRender[TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID] = utterenceId


    textToSpeech = TextToSpeech(
        context
    ) {
        if (it == TextToSpeech.SUCCESS) {
            textToSpeech?.let { txtToSpeech ->
                txtToSpeech.language = Locale.US
                txtToSpeech.setSpeechRate(1.0f)
                for (speakable in parts) {
                    if (count == 0) { // Use for the first splited text to flush on audio stream
                        textToSpeech!!.speak(speakable.trim { it <= ' ' }, TextToSpeech.QUEUE_FLUSH, myHashRender)
                    } else { // add the new test on previous then play the TTS
                        textToSpeech!!.speak(speakable.trim { it <= ' ' }, TextToSpeech.QUEUE_ADD, myHashRender)
                    }
                    textToSpeech!!.playSilentUtterance(750, TextToSpeech.QUEUE_ADD, null)
                    count++
                }
            }
        }

    }
}

private fun utterenceId(articleId: Int, count: Int): String {
    return "${articleId}_$count"
}