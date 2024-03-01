package viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import db.AppDb
import dto.Post
import repository.PostRepository
import repository.PostRepositoryInMemoryImpl
import repository.PostRepositorySQLiteImpl

private val empty = Post(
    id = 0,
    textView = "",
    textView2 = "",
    likedByMe = false,
    likes = 0,
    textView4 = ""
)
class PostViewModel(application: Application): AndroidViewModel(application) {
    private val repository: PostRepository = PostRepositorySQLiteImpl(
        AppDb.getInstance(application).postDao
    )
    val data = repository.getAll()
    val edited = MutableLiveData(empty)

    fun save() {
        edited.value?.let {
            repository.save(it)
        }
        edited.value = empty
    }

    fun edit(post: Post) {
        edited.value = post
    }

    fun changeContent(content: String) {
        val text = content.trim()
        if (edited.value?.textView == text) {
            return
        }
        edited.value = edited.value?.copy(textView = text)
    }

    fun likeById(id: Long) = repository.likeById(id)
    fun removeById(id: Long) = repository.removeById(id)
}