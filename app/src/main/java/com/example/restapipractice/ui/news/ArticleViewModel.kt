package com.example.restapipractice.ui.news

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.restapipractice.data.remote.api.ArticleDto
import com.example.restapipractice.data.repository.ArticleRepository
import kotlinx.coroutines.launch

class ArticleViewModel: ViewModel() {
    private val _articleRepository = ArticleRepository()
    var articleList = mutableStateOf<ArticleDto>(ArticleDto("", 0, 0, 0, emptyList()))
        private set

    fun fetchArticles() {
        viewModelScope.launch {
            try {
                articleList.value = _articleRepository.getArticles()
//                articleList = mutableStateOf(_articleRepository.getArticles())
//                val articles = _articleRepository.getArticles()
//                articleList.clear()
//                articleList.add(articles)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}