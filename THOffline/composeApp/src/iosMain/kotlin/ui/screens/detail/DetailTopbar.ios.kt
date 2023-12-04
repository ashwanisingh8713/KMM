package ui.screens.detail

import domain.mapper.ArticleMapper
import domain.model.Article
import platform.AVFAudio.AVSpeechSynthesisVoice
import platform.AVFAudio.AVSpeechSynthesizer
import platform.AVFAudio.AVSpeechUtterance
import platform.AVFAudio.AVSpeechUtteranceDefaultSpeechRate
import platform.UIKit.setAccessibilityLanguage

/**
 * Created by Ashwani Kumar Singh on 08,September,2023.
 */

actual fun onSharePressed(article: ArticleMapper) {

}
actual fun onCommentPress(article: ArticleMapper) {

}
actual fun onFontPress(descriptionFontSize: Int) {

}

actual fun onTextToSpeechPress(article: ArticleMapper) {


    startSpeechWith("", "", article.de!!)

}


private fun startSpeechWith(title: String, leadText: String, description:String){

    val synthesizer = AVSpeechSynthesizer()

    val rate = AVSpeechUtteranceDefaultSpeechRate
    val pitch = 1.0
    val preferredVoiceLanguageCode = "en-GB"

    val voice = AVSpeechSynthesisVoice()
    voice.setAccessibilityLanguage(preferredVoiceLanguageCode)

    val speechUtterance = AVSpeechUtterance(string = description)
    speechUtterance.setRate( rate)
    speechUtterance.setPitchMultiplier(pitch.toFloat())
    speechUtterance.setVoice(voice)
    speechUtterance.setPreUtteranceDelay(0.0)
    speechUtterance.speechString()

    synthesizer.speakUtterance(speechUtterance)

}