package com.example.newsapiapp.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.example.newsapiapp.api.NewsApi
import javax.inject.Inject

class NewsRepository @Inject constructor(private val newsApi: NewsApi) {
    fun getNews(query: String) =
        Pager(
            config = PagingConfig(
                pageSize = 20,
                maxSize = 100,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { NewsPagingSource(newsApi, query) }
        ).liveData
}