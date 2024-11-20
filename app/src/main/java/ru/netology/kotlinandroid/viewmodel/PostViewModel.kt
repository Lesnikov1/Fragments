package ru.netology.kotlinandroid.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.netology.kotlinandroid.dto.Post
import ru.netology.kotlinandroid.repository.PostRepository
import ru.netology.kotlinandroid.repository.PostRepositoryFilesImpl
import ru.netology.kotlinandroid.repository.PostRepositoryInMemoryImpl

private val empty = Post(
    id = 0L,
    author = "",
    content = "",
    publisher = "",
    likedByMe = false,
    video = null
)

class PostViewModel(application: Application ) : AndroidViewModel(application) {
    val edited = MutableLiveData(empty)
    private val repository: PostRepository = PostRepositoryFilesImpl(application)
    val data = repository.getAll()


    fun applyChangesAndSave(newText: String) {
        edited.value?.let {
            val text = newText.trim()
            if (text != it.content) {
                repository.save(it.copy(content = text))
            }
        }
        clearEdit()
    }
    fun edit(post: Post) {
        edited.value = post
    }

    fun clearEdit() {
        edited.value = empty
    }

    fun like(id: Long) = repository.like(id)
    fun removeById(id: Long) = repository.removeById(id)

}