package dto

data class Post(
    val id: Long,
    val textView2: String,
    val textView: String,
    val textView4: String,
    val likedByMe: Boolean,
    val likes: Int = 0,
)
