package com.example.newsapiapp.api

import com.example.newsapiapp.data.model.NewsPost

data class NewsResponse(
    val articles: List<NewsPost>
)