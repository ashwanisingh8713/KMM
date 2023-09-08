package util

import java.util.regex.Pattern

/**
 * Created by Ashwani Kumar Singh on 08,September,2023.
 */

fun getArticleIdFromArticleUrl(articleUrl: String): Int {
    val p = Pattern.compile("article(\\d+)")
    val m = p.matcher(articleUrl)
    var articleId = 0
    if (m.find()) {
        articleId = try {
            m.group(1).toInt()
        } catch (e: Exception) {
            return 0
        }
    }
    return articleId
}