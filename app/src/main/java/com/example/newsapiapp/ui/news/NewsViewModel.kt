package com.example.newsapiapp.ui.news

import androidx.lifecycle.*
import androidx.paging.cachedIn
import com.example.newsapiapp.data.NewsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(
    private val newsRepository: NewsRepository
) : ViewModel() {

    private val currentQuery = MutableLiveData(DEFAULT_QUERY)

    val news = currentQuery.switchMap { queryString ->
        newsRepository.getNews(queryString).cachedIn(viewModelScope)
    }

    fun searchNews(query: String) {
        currentQuery.value = query
    }

    companion object {
        private const val DEFAULT_QUERY = "android"
    }
}