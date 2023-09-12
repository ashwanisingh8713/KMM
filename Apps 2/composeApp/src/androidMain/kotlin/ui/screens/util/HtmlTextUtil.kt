package ui.screens.util

import android.text.Html
import java.util.Arrays
import java.util.Locale
import java.util.regex.Pattern

/**
 * Created by Ashwani Kumar Singh on 11,September,2023.
*/

object HtmlTextUtil  {
    private val PUNCTUATION = Pattern.compile("\\.( ?\\.)*[\"'”’]?|[\\?!] ?[\"'”’]?|, ?[\"'”’]|”")

    /*
    These are titles like Mr. Mrs., etc that will often cause incorrect
    breaks in English text. We filter them out.
     */
    private val TITLES = arrayOf("mr", "mrs", "dr", "ms", "st")

    /**
     * Processes an input string and enters a newline after full stops,
     * question marks, etc.
     *
     * @param input
     * @return
     */
    fun splitOnPunctuation(input: String): List<String> {
        val stringBuffer = StringBuffer()
        val matcher = PUNCTUATION.matcher(input)
        var previousMatch = 0
        while (matcher.find()) {
            val match = matcher.group()
            val startIndex = matcher.start()
            val subString = input.substring(previousMatch, startIndex)
            var shouldReplace = true
            for (title in TITLES) {
                if (subString.lowercase(Locale.getDefault()).endsWith(title!!)) {
                    shouldReplace = false
                }
            }
            if (subString.trim { it <= ' ' }.length == 1) {
                shouldReplace = false
            }
            var replacement: String
            replacement = if (shouldReplace) {
                """
     $match
     
     """.trimIndent()
            } else {
                match
            }
            matcher.appendReplacement(stringBuffer, replacement)
            previousMatch = startIndex
        }
        matcher.appendTail(stringBuffer)

            return Arrays.asList(
            *stringBuffer.toString().split("\n".toRegex()).dropLastWhile { it.isEmpty() }
                .toTypedArray())
    }

    fun shortenText(original: String): String? {
        var text = original
        if (text.length > 40) {
            text = text.substring(0, 40) + "…"
        }
        return text
    }

    private fun splitByNumber(s: String, chunkSize: Int): Array<String?>? {
        val chunkCount = s.length / chunkSize + if (s.length % chunkSize == 0) 0 else 1
        val returnVal = arrayOfNulls<String>(chunkCount)
        for (i in 0 until chunkCount) {
            returnVal[i] = s.substring(i * chunkSize, Math.min((i + 1) * chunkSize - 1, s.length))
        }
        return returnVal
    }

    fun speakableText(
        title: String,
        shortDescription: String?,
        description: String?
    ): String {
        var parsedShortDescription = ""
        var parsedDescription = ""
        if (shortDescription != null) {
            parsedShortDescription =
                Html.fromHtml(Html.fromHtml(shortDescription).toString())
                    .toString()
        }
        if (description != null) {
            parsedDescription =
                Html.fromHtml(Html.fromHtml(description).toString())
                    .toString()
        }
        return "$title...$parsedShortDescription...$parsedDescription"
    }
}