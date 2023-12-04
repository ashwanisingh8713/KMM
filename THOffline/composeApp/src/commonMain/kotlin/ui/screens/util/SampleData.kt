package ui.screens.util

import com.eygraber.uri.Uri

/**
 * Created by Ashwani Kumar Singh on 02,November,2023.
 */

data class Reel(
    val user: User,
    val description: String,
    val tags: List<String>,
    val music: Music,
    val likes: String,
    val comment: String,
    val video: String
) {

}

data class User(
    val profile: String,
    val username: String,
    val follow: Boolean,
)

data class Music(
    val name: String,
    val author: String,
    val isOriginalAudio: Boolean,
    val profile: String,
)

val dummyDataReels: List<Reel> = listOf(
    Reel(
        user = User(
            profile = "https://source.unsplash.com/random/100x100",
            username = "John Dow",
            follow = false,
        ),
        description = "is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book.",
        tags = listOf("#Music", "#Comedy", "#Morning"),
        music = Music(
            name = "Fly Me to The Moon",
            author = "",
            isOriginalAudio = true,
            profile = "https://source.unsplash.com/random/100x101",
        ),
        likes = "11K",
        comment = "23",
        video = "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4",
    ),
    Reel(
        user = User(
            profile = "https://source.unsplash.com/random/100x102",
            username = "kye_fry",
            follow = true,
        ),
        description = "My Happy Baby",
        tags = listOf("#babies", "#happy", "#smile", "#laugh"),
        music = Music(
            name = "kye_fry",
            author = "",
            isOriginalAudio = true,
            profile = "https://source.unsplash.com/random/100x103",
        ),
        likes = "19.8K",
        comment = "106",
        video = "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4",
    ),
    Reel(
        user = User(
            profile = "https://source.unsplash.com/random/100x104",
            username = "duniaindah",
            follow = false,
        ),
        description = "Beautiful Night is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book.",
        tags = listOf("#night", "#indonesia", "#malam", "#tidur"),
        music = Music(
            name = "WG Genk",
            author = "Genk Kotok",
            isOriginalAudio = false,
            profile = "https://source.unsplash.com/random/100x105",
        ),
        likes = "134K",
        comment = "1,512",
        video = "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4",
    ),
)